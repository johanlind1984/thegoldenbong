package com.atg.thegoldenbong.repository;

import com.atg.thegoldenbong.dto.Enum.ArchiveType;
import com.atg.thegoldenbong.entity.TrendResult;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface TrenderResultRepository extends JpaRepository<TrendResult, Long> {

    TrendResult findByGameIdAndRaceIdAndAndHorseId(final String gameId, final String raceId, final Integer horseId);

    TrendResult findByGameIdAndAndHorseId(final String gameId, final Integer horseId);

    List<TrendResult> findByGameId(final String gameId);

    List<TrendResult> findByGameIdAndRaceId(final String gameId, final String raceId);

    List<TrendResult> findTrendResultByArchiveTypeAndPlacement(ArchiveType archiveType, int placement);


    @Query("""
            SELECT tr.raceId
            FROM TrendResult tr
            WHERE NOT EXISTS (
                SELECT 1
                FROM TrendResult subtr
                WHERE subtr.raceId = tr.raceId AND subtr.placement = 1
            )
            GROUP BY tr.raceId
            """)
    List<String> findDistinctTrendResultRacesWithoutWinners();

    @Query("""
            select distinct tr.gameId
            FROM TrendResult tr
            WHERE tr.raceId = :raceId
            """)
    List<String> findGameIdByRace(String raceId);

    List<TrendResult> findTrendResultByHorseNumberAndGameIdAndRaceId(Integer horseNumber, String gameId, String raceId);

    List<TrendResult> findTrendResultByHorseNumberAndRaceId(Integer horseNumber, String raceId);

    boolean existsByRaceIdAndAndPlacement(String raceId, int placement);

    List<TrendResult> findTrendResultByPlacement(int placement);


}
