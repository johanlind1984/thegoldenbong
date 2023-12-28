package com.atg.thegoldenbong.batchjob;

import com.atg.thegoldenbong.dto.atg.CalendarGamesDto;
import com.atg.thegoldenbong.dto.atg.GameDto;
import com.atg.thegoldenbong.dto.atg.RacesDto;
import com.atg.thegoldenbong.dto.atg.VDto;
import com.atg.thegoldenbong.service.GameService;
import com.atg.thegoldenbong.service.TrendResultService;
import com.atg.thegoldenbong.service.TrenderService;
import com.fasterxml.jackson.core.JsonProcessingException;
import com.google.gson.Gson;
import jakarta.transaction.Transactional;
import lombok.extern.log4j.Log4j2;
import org.apache.logging.log4j.Level;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.stereotype.Component;
import org.springframework.web.client.RestTemplate;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.time.LocalDateTime;
import java.time.LocalTime;
import java.time.ZoneId;
import java.util.Date;

@Log4j2
@Component
public class TrendDataFetchBatchJob {

    private final String BASE_URI = "https://www.atg.se/services/racinginfo/v1/api/";
    final TrenderService trenderService;

    final TrendResultService trenderResultService;
    final GameService gameService;
    final Gson gson;


    @Autowired
    public TrendDataFetchBatchJob(TrenderService trenderService, TrendResultService trendResultService, GameService gameService) {
        this.trenderService = trenderService;
        this.trenderResultService = trendResultService;
        this.gameService = gameService;
        this.gson = new Gson();
    }
    final RestTemplate restTemplate = new RestTemplate();


    @Scheduled(fixedRate = 60000)
    public void execute() throws JsonProcessingException, ParseException {
        log.info("executing job " + this.getClass().getName());
        CalendarGamesDto calendarGamesDto = getTodayGames();

        for (final VDto vDto : calendarGamesDto.getAllGames()) {
            final String gameId = vDto.getId();
            final GameDto game = getGame(gameId);

            // setup times for start and now, only save new data if the race haven't started yet
            final SimpleDateFormat formatter = new SimpleDateFormat("yyyy-MM-dd'T'HH:mm:ss");
            final Date startTime = formatter.parse(game.getRaces().get(0).getScheduledStartTime());
            final Date now = new Date();

            if (startTime.after(now)) {
                log.info("saving trends for " + gameId);
                trenderService.saveDtoToDomain(game);
            } else {
                saveStatistics(game);
            }
        }

        log.info("completed job " + this.getClass().getName());
    }

    private void saveStatistics(GameDto game) throws ParseException {
        log.info("checking if saving statistics for game " + game.getId() + " is required");
        // check if the date
        final SimpleDateFormat formatter = new SimpleDateFormat("yyyy-MM-dd'T'HH:mm:ss");
        final Date startTime = formatter.parse(game.getRaces().get(0).getScheduledStartTime());
        final Date now = new Date();


        if (startTime.before(now)) {
            for (final RacesDto racesDto : game.getRaces()) {
                if (trenderResultService.findByGameAndRaceId(game.getId(), racesDto.getId()).isEmpty()) {
                    log.info("saving trendResults for race: " + racesDto.getId() + " and game: " + game.getId());
                    trenderResultService.saveGameTrendResults(game.getId(), racesDto.getId(), startTime);
                }
            }

        }
    }

    @Transactional
    @Scheduled(fixedRate = 120000)
    public void removeOldTrends() {
        LocalDateTime fourHoursAgo = LocalDateTime.now().minusHours(4);
        final Date date =  Date.from(fourHoursAgo.atZone(ZoneId.systemDefault()).toInstant());

        log.log(Level.INFO, "Removing old entries before date: " + date);
        trenderService.deleteTrendsBeforeDate(date);
    }

    public GameDto getGame(final String id)  {
        final String uri = BASE_URI + "games/" + id;
        final ResponseEntity<String> response = restTemplate.getForEntity(uri, String.class);
        final GameDto game = gson.fromJson(response.getBody(), GameDto.class);
        return game;
    }

    private CalendarGamesDto getTodayGames() throws JsonProcessingException {
        return gameService.getTodayGames();
    }
}
