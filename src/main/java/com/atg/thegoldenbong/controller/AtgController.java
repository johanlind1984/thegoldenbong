package com.atg.thegoldenbong.controller;

import com.atg.thegoldenbong.dto.Enum.ArchiveType;
import com.atg.thegoldenbong.dto.Enum.TrendResultTimeStrategy;
import com.atg.thegoldenbong.dto.TrenderDto;
import com.atg.thegoldenbong.dto.TrenderMultisetDto;
import com.atg.thegoldenbong.dto.atg.GameDto;
import com.atg.thegoldenbong.entity.TrendResult;
import com.atg.thegoldenbong.service.GameService;
import com.atg.thegoldenbong.service.TrendResultService;
import com.atg.thegoldenbong.service.TrenderService;
import com.fasterxml.jackson.core.JsonProcessingException;
import com.google.gson.Gson;
import lombok.extern.log4j.Log4j2;
import org.apache.logging.log4j.Level;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.client.RestTemplate;

import java.util.*;

@Log4j2
@RestController
public class AtgController {
    private final String BASE_URI = "https://www.atg.se/services/racinginfo/v1/api/";
    final Gson gson;
    final GameService gameService;
    final TrenderService trenderService;

    final TrendResultService trendResultService;

    @Autowired
    public AtgController(Gson gson,  GameService gameService, TrenderService trenderService, TrendResultService trendResultService) {
        this.gameService = gameService;
        this.trenderService = trenderService;
        this.trendResultService = trendResultService;
        this.gson = gson;
    }

    @GetMapping("/game/{id}")
    public GameDto getGame(@PathVariable final String id) {
        final String uri = BASE_URI + "games/" + id;
        final RestTemplate restTemplate = new RestTemplate();
        final ResponseEntity<String> response = restTemplate.getForEntity(uri, String.class);
        final GameDto gameDto = gson.fromJson(response.getBody(), GameDto.class);
        return gson.fromJson(response.getBody(), GameDto.class);
    }

    @GetMapping("/game/getgames")
    public List<String> getGameIds() throws JsonProcessingException {
        return gameService.getTodaysGameIds();
    }

    @GetMapping("/trender/{gameId}")
    public Map<String, List<TrenderDto>> getTrender(@PathVariable final String gameId) {
        log.log(Level.INFO, "getTrender called for gameId: " + gameId);

        return trenderService.getTrenderSummary(gameId, Optional.empty());
    }

    @GetMapping("/trender/{gameId}/{hours}/{minutes}")
    public Map<String, List<TrenderDto>> getTrenderLastHours(@PathVariable final String gameId, @PathVariable final Integer hours, @PathVariable Integer minutes) {
        final Date timeStamp = new Date(System.currentTimeMillis() - ((3_600L * hours * 1_000) + (60L * minutes * 1_000)));
        log.log(Level.INFO, "getTrenderLastHours(" + timeStamp + ") called for gameId: " + gameId);

        return trenderService.getTrenderSummary(gameId, Optional.of(timeStamp));
    }

    @GetMapping("/trender/{gameType}/{hoursBeforeStart}/{minutesBeforeStart}/{hoursEnd}/{minutesEnd}")
    public Map<String, List<TrenderDto>> getTrenderWithinHoursAndMinutes(
            @PathVariable final String gameType,
            @PathVariable final Integer hoursBeforeStart,
            @PathVariable Integer minutesBeforeStart,
            @PathVariable final Integer hoursEnd,
            @PathVariable Integer minutesEnd) {

        final ArchiveType archiveType = ArchiveType.valueOf(gameType);

        return null;
    }

    @GetMapping("/trender/{gameId}/multiset")
    public Map<String, List<TrenderMultisetDto>> getTrenderMultiset(@PathVariable final String gameId) {
        log.log(Level.INFO, "getTrenderMultiset() called for gameId: " + gameId);
        return trenderService.getTrenderMultiset(gameId, Optional.empty());

    }

    @GetMapping("/trender/horses/{gameId}/{hours}/{minutes}")
    public Map<Integer, List<TrenderDto>> getHorsesTrenderLastHours(@PathVariable final String gameId, @PathVariable final Integer hours, @PathVariable Integer minutes) {
        final Date timeStamp = new Date(System.currentTimeMillis() - ((3_600L * hours * 1_000) + (60L * minutes * 1_000)));
        log.log(Level.INFO, "getTrenderLastHours(" + timeStamp + ") called for gameId: " + gameId);

        return trenderService.getAllHorsesTrender(gameId, Optional.of(timeStamp));
    }

    @GetMapping("/trender/archive/{archiveType}")
    public List<TrendResult> getArchived(@PathVariable final String archiveType) {
        log.log(Level.INFO, "getArchived(" + archiveType + ") called");
        final ArchiveType type = ArchiveType.valueOf(archiveType);

        if (type == ArchiveType.ALL_WINNERS) {
            return trendResultService.findAllTrendResultWinners();
        }

        return trendResultService.findTrendResultWinnersByArchiveTypeAndTrendResultTimeStrategy(type, TrendResultTimeStrategy.FIVE_MINUTES_BEFORE_START);
    }

    @GetMapping("/trender/archive/{archiveType}/{lowVdist}/{highVdist}")
    public List<TrendResult> getArchived(@PathVariable final String archiveType, @PathVariable final int lowVdist, @PathVariable final int highVdist) {
        log.log(Level.INFO, "getArchived(" + archiveType + ") called");
        final ArchiveType type = ArchiveType.valueOf(archiveType);

        if (type == ArchiveType.ALL_WINNERS) {
            return trendResultService.findTrendResultByPlacementAndVDistribution0Between(1, lowVdist, highVdist);
        }

        return trendResultService.findTrendResultWinnersByArchiveTypeTrendResultTimeStrategyAndVDistribution0Between(type, TrendResultTimeStrategy.FIVE_MINUTES_BEFORE_START, lowVdist, highVdist);
    }

    @GetMapping("/trender/archivestatistics/{archiveType}/{lowVdist}/{highVdist}")
    public List<String> getArchivedStatistics(@PathVariable final String archiveType, @PathVariable final int lowVdist, @PathVariable final int highVdist) {
        log.log(Level.INFO, "getArchivedStatistics(" + archiveType + ") called");
        final ArchiveType type = ArchiveType.valueOf(archiveType);

        return trendResultService.findTrendResultStatisticsWinnersByArchiveType(type, lowVdist, highVdist);
    }

    @GetMapping("/trender/archivestatistics/timed/{archiveType}/{lowVdist}/{highVdist}/{startMinutesBeforeStart}/{endMinutesBeforeStart}")
    public Map<String, List<TrenderDto>> getTimedArchivedStatistics(
            @PathVariable final String archiveType,
        @PathVariable final int lowVdist,
        @PathVariable final int highVdist,
        @PathVariable final int startMinutesBeforeStart,
        @PathVariable final int endMinutesBeforeStart) {

        // loop for each game

/*        for ()
        {
            final Date timeStamp = new Date(System.currentTimeMillis() - ((3_600L * startMinutesBeforeStart * 1_000) + (60L * endMinutesBeforeStart * 1_000)));
            log.log(Level.INFO, "getTrenderLastHours(" + timeStamp + ") called for gameId: " + gameId);

        }*/

        //return trenderService.getTrenderSummary(gameId, Optional.of(timeStamp));

        return null;
    }
}
