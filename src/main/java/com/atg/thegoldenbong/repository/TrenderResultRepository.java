package com.atg.thegoldenbong.repository;

import com.atg.thegoldenbong.entity.TrendResult;
import com.atg.thegoldenbong.entity.Trender;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.Date;
import java.util.List;

@Repository
public interface TrenderResultRepository extends JpaRepository<TrendResult, Long> {

    TrendResult findByGameIdAndRaceIdAndAndHorseId(final String gameId, final String raceId, final Integer horseId);

    TrendResult findByGameIdAndAndHorseId(final String gameId, final Integer horseId);

    List<TrendResult> findByGameId(final String gameId);

    List<TrendResult> findByGameIdAndRaceId(final String gameId, final String raceId);

}
