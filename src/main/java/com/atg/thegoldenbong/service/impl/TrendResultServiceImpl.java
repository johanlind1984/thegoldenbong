package com.atg.thegoldenbong.service.impl;

import com.atg.thegoldenbong.dto.Enum.ArchiveType;
import com.atg.thegoldenbong.dto.Enum.TrendResultTimeStrategy;
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

    private final int SAVE_TRENDRESULT_MINUTES_BEFORE_START = -5;

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
            // this is the only strategy we use at the moment
            trendResult.setTrendResultTimeStrategy(TrendResultTimeStrategy.FIVE_MINUTES_BEFORE_START);
            saveTrendResultForDate(gameId, raceId, horse, startTime, trendResult);
            });
    }

    @Override
    public List<TrendResult> findTrendResultWinnersByArchiveType(ArchiveType archiveType) {
        return trenderResultRepository.findTrendResultByArchiveTypeAndPlacement(archiveType, 1);
    }

    @Override
    public List<TrendResult> findTrendResultByPlacementAndVDistribution0Between(int placement, int vDistLow, int vDistHigh) {
        return trenderResultRepository.findTrendResultByPlacement(placement)
                .stream()
                .filter(trendResult -> trendResult.getVDistribution0() > vDistLow && trendResult.getVDistribution0() < vDistHigh)
                .toList();
    }

    @Override
    public List<TrendResult> findTrendResultWinnersByArchiveTypeAndVDistribution0Between(ArchiveType archiveType, int vDistLow, int vDistHigh) {
        return trenderResultRepository.findTrendResultByArchiveTypeAndPlacement(archiveType, 1)
                .stream()
                .filter(trendResult -> trendResult.getVDistribution0() > vDistLow && trendResult.getVDistribution0() < vDistHigh)
                .toList();
    }

    @Override
    public List<String> findTrendResultStatisticsWinnersByArchiveType(ArchiveType archiveType, int lowVist, int highVdist) {

        List<TrendResult> allWinners = new ArrayList<>();

        if (archiveType == ArchiveType.ALL_WINNERS) {
            allWinners = trenderResultRepository.findTrendResultByPlacement(1);
        } else {
            allWinners = trenderResultRepository.findTrendResultByArchiveTypeAndPlacement(archiveType, 1);
        }

        final List<TrendResult> trendResultWinners = allWinners
                .stream()
                .filter(result -> result.getVDistribution0() > lowVist && result.getVDistribution0() < highVdist)
                .toList();

        int positiveVdist15 = 0;
        int positiveVdist30 = 0;
        int positiveVdist60 = 0;

        for (final TrendResult trendResult : trendResultWinners) {
            if (trendResult.getVDistribution15() != null && trendResult.getVDistribution30() != null && trendResult.getVDistribution60() != null) {
                final long vDist0 = trendResult.getVDistribution0();
                final long vDist15 = trendResult.getVDistribution0() - trendResult.getVDistribution15();
                final long vDist30 = trendResult.getVDistribution0() - trendResult.getVDistribution30();
                final long vDist60 = trendResult.getVDistribution0() - trendResult.getVDistribution60();

                if (vDist15 > 0) {
                    positiveVdist15 += 1L;
                }

                if (vDist30 > 0) {
                    positiveVdist30 += 1L;
                }

                if (vDist60 > 0) {
                    positiveVdist60 += 1L;
                }
            }
        }

        final double totalResults = trendResultWinners.size();
        final double winnersPercent = (totalResults / allWinners.size()) * 100;
        final double winner15percent = (positiveVdist15 / totalResults) * 100;
        final double winner30percent = (positiveVdist30 / totalResults) * 100;
        final double winner60percent = (positiveVdist60 / totalResults) * 100;

        final List<String> statistics = new ArrayList<>();
        statistics.add(archiveType + " between vdist: " + lowVist + " and " + highVdist);
        statistics.add("The percentage of winners within this vDist is: " + winnersPercent + "%");
        statistics.add("Positive vDist15: " + winner15percent + "%");
        statistics.add("Positive vDist30: " + winner30percent + "%");
        statistics.add("Positive vDist60: " + winner60percent + "%");

        return statistics;
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

        calendar.add(Calendar.MINUTE, SAVE_TRENDRESULT_MINUTES_BEFORE_START);
        final Date beforeMinutesBeforeStart = calendar.getTime();
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

        final int beforeMinEntry = IntStream.range(0, trenderList.size())
                .filter(i -> trenderList.get(i).getTimeStamp().before(beforeMinutesBeforeStart))
                .findFirst()
                .orElse(-1);


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

        if (beforeMinEntry != -1) {
            //SAVE_TRENDRESULT_MINUTES_BEFORE_START minutes from start
            final long vDistributionBeforeStart = trenderList.get(beforeMinEntry).getVDistribution();
            final long vOddsBeforeStart = trenderList.get(beforeMinEntry).getVOdds();
            trendResult.setVDistribution0(vDistributionBeforeStart);
            trendResult.setVDistribution0(vOddsBeforeStart);
        } else {
            log.warn(String.format("Could not find trends for SAVE_TRENDRESULT_MINUTES_BEFORE_START mins for horseId: %s, raceId: %s, gameId: %s, startime: %s", horseId, raceId, gameId, startTime));
        }

        trenderResultRepository.save(trendResult);
    }
}
