package com.atg.thegoldenbong.service;

import com.atg.thegoldenbong.dto.Enum.ArchiveType;
import com.atg.thegoldenbong.entity.TrendResult;

import java.util.Date;
import java.util.List;
import java.util.Map;

public interface TrendResultService {
    TrendResult findByGameIdAndAndHorseId(final String gameId, final Integer horseId);
    List<TrendResult> findByGame(String gameId);

    List<TrendResult> findByGameAndRaceId(String gameId, String raceId);
    void saveGameTrendResults(final String gameId, final String raceID, Date startTime);

    List<TrendResult> findTrendResultWinnersByArchiveType(ArchiveType archiveType);
}
