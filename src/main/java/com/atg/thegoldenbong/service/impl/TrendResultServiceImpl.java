package com.atg.thegoldenbong.service.impl;

import com.atg.thegoldenbong.dto.Enum.ArchiveType;
import com.atg.thegoldenbong.dto.atg.HorseDto;
import com.atg.thegoldenbong.entity.TrendResult;
import com.atg.thegoldenbong.entity.Trender;
import com.atg.thegoldenbong.repository.TrenderRepository;
import com.atg.thegoldenbong.repository.TrenderResultRepository;
import com.atg.thegoldenbong.service.TrendResultService;
import lombok.extern.log4j.Log4j2;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.*;
import java.util.stream.IntStream;

@Log4j2
@Service
public class TrendResultServiceImpl implements TrendResultService {

    private TrenderResultRepository trenderResultRepository;
    private TrenderRepository trenderRepository;

    @Autowired
    public TrendResultServiceImpl(TrenderResultRepository trenderResultRepository, TrenderRepository trenderRepository) {
        this.trenderResultRepository = trenderResultRepository;
        this.trenderRepository = trenderRepository;
    }

    @Override
    public TrendResult findByGameIdAndAndHorseId(String gameId, Integer horseId) {
        return trenderResultRepository.findByGameIdAndAndHorseId(gameId, horseId);
    }

    @Override
    public List<TrendResult> findByGame(String gameId) {
        return trenderResultRepository.findByGameId(gameId);
    }

    @Override
    public List<TrendResult> findByGameAndRaceId(String gameId, String raceId) {
        return trenderResultRepository.findByGameIdAndRaceId(gameId, raceId);
    }

    @Override
    public void saveGameTrendResults(final String gameId, String raceId, Date startTime) {
        List<Integer> horseIdList = trenderRepository.findDistinctHorseIdByGameIdAAndRaceIdOrderByTimeStampAsc(gameId, raceId);

        horseIdList.forEach(horse -> {
            log.info(String.format("Saving trendResult for horseId: %s, gameId: %s, raceId: %s, startTime: %s", horse, gameId, raceId, startTime));
            TrendResult trendResult = new TrendResult();
            trendResult.setGameId(gameId);
            trendResult.setRaceId(raceId);
            trendResult.setHorseId(horse);
            trendResult.setArchiveType(getArchiveType(gameId));
            saveTrendResultForDate(gameId, raceId, horse, startTime, trendResult);

            });

    }

    @Override
    public List<TrendResult> findTrendResultWinnersByArchiveType(ArchiveType archiveType) {
        return trenderResultRepository.findTrendResultByArchiveTypeAndPlacement(archiveType, 1);
    }

    @Override
    public List<String> findRacesWithoutResults() {
        return trenderResultRepository.findDistinctTrendResultRacesWithoutWinners();
    }

    @Override
    public List<String> findGameIdByRace(String raceId) {
        return trenderResultRepository.findGameIdByRace(raceId);
    }


    @Override
    public List<TrendResult> findTrendResultByHorseNumberAndGameIdAndRaceIdAndPosition(Integer horseNumber, String gameId, String raceId) {
        return trenderResultRepository.findTrendResultByHorseNumberAndGameIdAndRaceId(horseNumber, gameId, raceId);
    }

    @Override
    public List<TrendResult> findTrendResultByHorseNumberAndRaceId(Integer horseNumber, String raceId) {
        return trenderResultRepository.findTrendResultByHorseNumberAndRaceId(horseNumber, raceId);
    }

    @Override
    public boolean haveRaceWinner(String raceId) {
        return trenderResultRepository.existsByRaceIdAndAndPlacement(raceId, 1);
    }

    @Override
    public List<TrendResult> findAllTrendResultWinners() {
        return trenderResultRepository.findTrendResultByPlacement(1);
    }

    @Override
    public void save(TrendResult trendResult) {
        trenderResultRepository.save(trendResult);
    }

    private Integer generateHorseId(HorseDto horse) {
        return Math.abs(horse.getName().hashCode());
    }

    private ArchiveType getArchiveType(String gameId) {
        return ArchiveType.ofGameId(gameId);
    }

    private void saveTrendResultForDate(String gameId, String raceId, Integer horseId, Date startTime, TrendResult trendResult) {

        Calendar calendar = Calendar.getInstance();
        calendar.setTime(startTime);
        calendar.add(Calendar.HOUR_OF_DAY, -1);
        final Date sixtyMinutesBeforeStart = calendar.getTime();
        calendar.add(Calendar.MINUTE, 30);
        final Date thirtyMinutesBeforeStart = calendar.getTime();
        calendar.add(Calendar.MINUTE, 15);
        final Date fifteenMinutesBeforeStart = calendar.getTime();

        List<Trender> trenderList = trenderRepository.findByGameIdAndRaceIdAndHorseIdAndTimeStampBetweenOrderByTimeStampDesc(gameId, raceId, horseId , sixtyMinutesBeforeStart, startTime);

        if (trenderList.isEmpty()) {
            log.info("could not find any trends for raceId: " + raceId + " and horse id: " + horseId + " between starttime: " + startTime + " and " + sixtyMinutesBeforeStart);
            return;
        } else if (Optional.ofNullable(trenderResultRepository.findByGameIdAndRaceIdAndAndHorseId(gameId, raceId, horseId)).isPresent()) {
            return;
        }

        // common data can be set by the first entry
        Trender firstTrend = trenderList.get(0);
        trendResult.setRaceNumber(firstTrend.getRaceNumber());
        trendResult.setRaceId(firstTrend.getRaceId());
        trendResult.setHorseName(firstTrend.getHorseName());
        trendResult.setHorseNumber(firstTrend.getHorseNumber());
        trendResult.setHorseId(firstTrend.getHorseId());

        final int lastEntry = trenderList.size() - 1;
        final int sixtyMinEntry = trenderList.size() -1;

        final int thirtyMinEntry = IntStream.range(0, trenderList.size())
                .filter(i -> trenderList.get(i).getTimeStamp().before(thirtyMinutesBeforeStart))
                .findFirst()
                .orElse(-1);

        final int fifteenMinEntry = IntStream.range(0, trenderList.size())
                .filter(i -> trenderList.get(i).getTimeStamp().before(fifteenMinutesBeforeStart))
                .findFirst()
                .orElse(-1);

        // sixty minutes from start
        if (sixtyMinEntry != -1) {
            final long vDistribution60 = trenderList.get(sixtyMinEntry).getVDistribution();
            final long vOdds60 = trenderList.get(sixtyMinEntry).getVOdds();
            trendResult.setVDistribution60(vDistribution60);
            trendResult.setVOdds60(vOdds60);
        } else {
            log.warn(String.format("Could not find trends for 60 mins for horseId: %s, raceId: %s, gameId: %s, startime: %s", horseId, raceId, gameId, startTime));
        }

        if (thirtyMinEntry != -1) {
            //thirty minutes from start
            final long vDistribution30 = trenderList.get(thirtyMinEntry).getVDistribution();
            final long vOdds30 = trenderList.get(thirtyMinEntry).getVOdds();
            trendResult.setVDistribution30(vDistribution30);
            trendResult.setVOdds30(vOdds30);
        } else {
            log.warn(String.format("Could not find trends for 30 mins for horseId: %s, raceId: %s, gameId: %s, startime: %s", horseId, raceId, gameId, startTime));
        }

        if (fifteenMinEntry != -1) {
            //fifteen minutes from start
            final long vDistribution15 = trenderList.get(fifteenMinEntry).getVDistribution();
            final long vOdds15 = trenderList.get(fifteenMinEntry).getVOdds();
            trendResult.setVDistribution15(vDistribution15);
            trendResult.setVOdds15(vOdds15);
        } else {
            log.warn(String.format("Could not find trends for 15 mins for horseId: %s, raceId: %s, gameId: %s, startime: %s", horseId, raceId, gameId, startTime));
        }

        //at start
        final long vDistribution0 = trenderList.get(0).getVDistribution();
        final long vOdds0 = trenderList.get(0).getVOdds();
        trendResult.setVDistribution0(vDistribution0);
        trendResult.setVOdds0(vOdds0);

        trenderResultRepository.save(trendResult);
    }
}
