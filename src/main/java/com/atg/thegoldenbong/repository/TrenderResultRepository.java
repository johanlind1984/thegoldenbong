package com.atg.thegoldenbong.repository;

import com.atg.thegoldenbong.dto.Enum.ArchiveType;
import com.atg.thegoldenbong.entity.TrendResult;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Map;

@Repository
public interface TrenderResultRepository extends JpaRepository<TrendResult, Long> {

    TrendResult findByGameIdAndRaceIdAndAndHorseId(final String gameId, final String raceId, final Integer horseId);

    TrendResult findByGameIdAndAndHorseId(final String gameId, final Integer horseId);

    List<TrendResult> findByGameId(final String gameId);

    List<TrendResult> findByGameIdAndRaceId(final String gameId, final String raceId);

    List<TrendResult> findTrendResultByArchiveTypeAndPlacement(ArchiveType archiveType, int placement);

}
