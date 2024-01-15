package com.atg.thegoldenbong.service;

import com.atg.thegoldenbong.dto.Enum.ArchiveType;
import com.atg.thegoldenbong.dto.Enum.TrendResultTimeStrategy;
import com.atg.thegoldenbong.entity.TrendResult;

import java.util.Date;
import java.util.List;

public interface TrendResultService {

    List<TrendResult> findByGameAndRaceId(String gameId, String raceId);
    void saveGameTrendResults(final String gameId, final String raceID, Date startTime);

    List<TrendResult> findTrendResultWinnersByArchiveTypeAndTrendResultTimeStrategy(ArchiveType archiveType, TrendResultTimeStrategy strategy);

    List<TrendResult> findTrendResultByPlacementAndVDistribution0Between(int placement, int vDistLow, int vDistHigh);

    List<TrendResult> findTrendResultWinnersByArchiveTypeTrendResultTimeStrategyAndVDistribution0Between(ArchiveType archiveType, TrendResultTimeStrategy strategy, int lowVdist, int highVdist);

    List<String> findTrendResultStatisticsWinnersByArchiveType(ArchiveType archiveType, int lowVdist, int highVist);

    List<String> findRacesWithoutResults();

    List<String> findGameIdByRace(String raceId);

    List<TrendResult> findTrendResultByHorseNumberAndRaceId(Integer horseNumber, String raceId);

    boolean haveRaceWinner(String raceId);

    List<TrendResult> findAllTrendResultWinners();

    void save(TrendResult trendResult);

}
