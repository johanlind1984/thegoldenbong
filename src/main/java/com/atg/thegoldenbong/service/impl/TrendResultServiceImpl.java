package com.atg.thegoldenbong.service.impl;

import com.atg.thegoldenbong.dto.atg.HorseDto;
import com.atg.thegoldenbong.entity.TrendResult;
import com.atg.thegoldenbong.entity.Trender;
import com.atg.thegoldenbong.repository.TrenderRepository;
import com.atg.thegoldenbong.repository.TrenderResultRepository;
import com.atg.thegoldenbong.service.TrendResultService;
import lombok.extern.log4j.Log4j2;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.Calendar;
import java.util.Date;
import java.util.List;
import java.util.Optional;
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
        List<Integer> horseIdList = trenderRepository.findDistinctHorseIdByGameIdOrderByTimeStampAsc(gameId);

        horseIdList.forEach(horse -> {
            TrendResult trendResult = new TrendResult();
            trendResult.setGameId(gameId);
            trendResult.setRaceId(raceId);
            trendResult.setHorseId(horse);
            saveTrendResultForDate(raceId, gameId, horse, startTime);

            });

    }

    private void saveTrendResultForDate(String raceId, String gameId, Integer horseId, Date startTime) {

        Calendar calendar = Calendar.getInstance();
        calendar.setTime(startTime);
        calendar.add(Calendar.HOUR_OF_DAY, -1);
        final Date oneHourAgo = calendar.getTime();
        calendar.add(Calendar.MINUTE, 30);
        final Date thirtyMinutesAgo = calendar.getTime();
        calendar.add(Calendar.MINUTE, 15);
        final Date fifteenMinutesAgo = calendar.getTime();

        List<Trender> trenderList = trenderRepository.findByRaceIdAndHorseIdAndTimeStampBetweenOrderByTimeStampDesc(raceId, horseId, startTime, oneHourAgo);

        TrendResult trendResult = new TrendResult();
        // common data can be set by the first entry
        Trender firstTrend = trenderList.get(0);
        trendResult.setRaceNumber(firstTrend.getRaceNumber());
        trendResult.setHorseName(firstTrend.getHorseName());
        trendResult.setHorseNumber(firstTrend.getHorseNumber());

        final int lastEntry = trenderList.size() - 1;
        final int sixtyMinEntry = 0;
        final int thirtyMinEntry = IntStream.range(0, trenderList.size())
                .filter(i -> trenderList.get(i).getTimeStamp().after(thirtyMinutesAgo))
                .findFirst()
                .orElse(-1);

        final int fifteenMinEntry = IntStream.range(0, trenderList.size())
                .filter(i -> trenderList.get(i).getTimeStamp().after(fifteenMinutesAgo))
                .findFirst()
                .orElse(-1);

        // sixty minutes from start
        final long vDistribution60 = trenderList.get(lastEntry).getVDistribution() - trenderList.get(sixtyMinEntry).getVDistribution();
        final long vOdds60 = trenderList.get(lastEntry).getVOdds() - trenderList.get(sixtyMinEntry).getVOdds();
        trendResult.setDistribution60(vDistribution60);
        trendResult.setVOdds60(vOdds60);

        //thirty minutes from start
        final long vDistribution30 = trenderList.get(lastEntry).getVDistribution() - trenderList.get(thirtyMinEntry).getVDistribution();
        final long vOdds30 = trenderList.get(lastEntry).getVOdds() - trenderList.get(thirtyMinEntry).getVOdds();
        trendResult.setDistribution30(vDistribution30);
        trendResult.setVOdds30(vOdds30);

        //fifteen minutes from start
        final long vDistribution15 = trenderList.get(lastEntry).getVDistribution() - trenderList.get(fifteenMinEntry).getVDistribution();
        final long vOdds15 = trenderList.get(lastEntry).getVOdds() - trenderList.get(fifteenMinEntry).getVOdds();
        trendResult.setDistribution15(vDistribution15);
        trendResult.setVOdds15(vOdds15);
        trenderResultRepository.save(trendResult);
    }

    private Integer generateHorseId(HorseDto horse) {
        return Math.abs(horse.getName().hashCode());
    }
}
