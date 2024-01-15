package com.atg.thegoldenbong.repository;

import com.atg.thegoldenbong.dto.Enum.ArchiveType;
import com.atg.thegoldenbong.entity.TrendResult;
import com.atg.thegoldenbong.entity.Trender;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

import java.util.Date;
import java.util.List;

@Repository
public interface TrenderRepository extends JpaRepository<Trender, Long> {
    List<Trender> findByRaceId(final String raceId);
    List<Trender> findByGameIdOrderByTimeStampDesc(final String gameId);
    @Query("SELECT DISTINCT t.horseId FROM Trender t WHERE t.gameId = :gameId AND t.raceId = :raceId")
    List<Integer> findDistinctHorseIdByGameIdAAndRaceIdOrderByTimeStampAsc(String gameId, String raceId);

    List<Trender> findByGameIdAndAndHorseIdOrderByTimeStampAsc(final String gameId, final Integer horseId);
    List<Trender> findByRaceIdAndAndHorseIdOrderByTimeStampAsc(final String raceId, final Integer horseId);
    List<Trender> findByRaceIdAndAndHorseIdOrderByTimeStampDesc(final String raceId, final Integer horseId);

    List<Trender> findByGameIdAndRaceIdAndHorseIdAndTimeStampBetweenOrderByTimeStampDesc(
            String gameId,
            String raceId,
            Integer horseId,
            Date startDate,
            Date endDate
    );

    List<Trender> findByGameIdAndAndHorseNameOrderByTimeStampAsc(final String gameId, final String horseName);

    List<Trender> findByGameIdAndAndHorseIdAndTimeStampIsAfterOrderByTimeStampAsc(final String gameId, final Integer horseId, final Date date);
    List<Trender> findByGameIdAndAndHorseNameAndTimeStampIsAfterOrderByTimeStampAsc(final String gameId, final String horseName, final Date date);

    void deleteAllByTimeStampBefore(final Date date);

    @Modifying
    @Query(value = "DELETE FROM trender WHERE timestamp < DATE_SUB(game_start_time, INTERVAL 2 HOUR)", nativeQuery = true)
    void deleteTrendsOlderThanTwoHoursBeforeStart();

}
