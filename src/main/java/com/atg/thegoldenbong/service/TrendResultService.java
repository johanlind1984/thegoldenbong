package com.atg.thegoldenbong.service;

import com.atg.thegoldenbong.dto.Enum.ArchiveType;
import com.atg.thegoldenbong.entity.TrendResult;

import java.util.Date;
import java.util.List;

public interface TrendResultService {
    TrendResult findByGameIdAndAndHorseId(final String gameId, final Integer horseId);
    List<TrendResult> findByGame(String gameId);

    List<TrendResult> findByGameAndRaceId(String gameId, String raceId);
    void saveGameTrendResults(final String gameId, final String raceID, Date startTime);

    List<TrendResult> findTrendResultWinnersByArchiveType(ArchiveType archiveType);

    List<String> findTrendResultStatisticsWinnersByArchiveType(ArchiveType archiveType, int lowVdist, int highVist);

    List<String> findRacesWithoutResults();

    List<String> findGameIdByRace(String raceId);

    List<TrendResult> findTrendResultByHorseNumberAndGameIdAndRaceIdAndPosition(Integer horseId, String gameId, String raceId);

    List<TrendResult> findTrendResultByHorseNumberAndRaceId(Integer horseNumber, String raceId);

    boolean haveRaceWinner(String raceId);

    List<TrendResult> findAllTrendResultWinners();

    void save(TrendResult trendResult);

}
