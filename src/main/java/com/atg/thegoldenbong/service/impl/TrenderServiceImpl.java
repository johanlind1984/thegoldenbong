package com.atg.thegoldenbong.service.impl;

import com.atg.thegoldenbong.dto.TrenderDto;
import com.atg.thegoldenbong.dto.TrenderMultisetDto;
import com.atg.thegoldenbong.repository.TrenderRepository;
import com.atg.thegoldenbong.dto.atg.GameDto;
import com.atg.thegoldenbong.dto.atg.HorseDto;
import com.atg.thegoldenbong.entity.Trender;
import com.atg.thegoldenbong.service.GameService;
import com.atg.thegoldenbong.service.TrenderService;
import com.google.gson.Gson;
import lombok.extern.log4j.Log4j2;
import org.apache.logging.log4j.Level;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.web.client.RestTemplate;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.time.LocalDate;
import java.util.*;
import java.util.stream.Collectors;
import java.util.stream.IntStream;

@Log4j2
@Service
public class TrenderServiceImpl implements TrenderService {

    private final TrenderRepository trenderRepository;

    @Autowired
    public TrenderServiceImpl(TrenderRepository trenderRepository, GameService gameService) {
        this.trenderRepository = trenderRepository;
    }

    @Override
    public void saveDtoToDomain(final GameDto gameDto) throws ParseException {
        final SimpleDateFormat formatter = new SimpleDateFormat("yyyy-MM-dd'T'HH:mm:ss");
        final Date startTime = formatter.parse(gameDto.getRaces().get(0).getScheduledStartTime());

        gameDto.getRaces().forEach(race -> race.getStarts().forEach(start -> {

            try {

                Trender trender = new Trender();
                trender.setGameId(gameDto.getId());
                trender.setRaceId(race.getId());
                trender.setRaceNumber(race.getNumber());
                trender.setStartTime(startTime);

                final Optional<HorseDto> horseDto = Optional.ofNullable(start.getHorse());

                final String horseName = horseDto.isPresent() ? horseDto.get().getName() : "NONAME";
                trender.setHorseName(horseName);

                Integer horseId = start.getHorse().getId() == 0 ? generateHorseId(start.getHorse()) : start.getHorse().getId();
                trender.setHorseId(horseId);

                trender.setHorseNumber(start.getNumber());

                if (Optional.ofNullable(start.getPools().getV()).isPresent()) {
                    trender.setVDistribution(start.getPools().getV().getBetDistribution());
                }

                if (Optional.ofNullable(start.getPools().getVinnare()).isPresent()) {
                    Optional.ofNullable(start.getPools().getVinnare().getOdds())
                            .ifPresent(trender::setVOdds);
                }

                trenderRepository.save(trender);
            } catch (Exception e) {
                log.error("Error saving trend for race: " + race.getId(), e);
            }
        }));
    }

    @Override
    public Map<String, List<TrenderDto>> getTrenderSummary(final String gameId, final Optional<Date> afterDate) {
        final List<Trender> trenderList = trenderRepository.findByGameIdOrderByTimeStampDesc(gameId);

        Map<String, List<TrenderDto>> trenderDtos = new HashMap<>();

        Set<Integer> horseIds = trenderList.stream().map(Trender::getHorseId).collect(Collectors.toSet());

        horseIds.forEach(horseId -> {
            List<Trender> orderedTrender = new ArrayList<>();

            if (afterDate.isPresent()) {
                Date date = afterDate.get();

                // if the race started before afterDate theres no data. so set date to 30 minutes before start
                if (date.after(trenderList.get(0).getTimeStamp())) {
                    Calendar calendar = Calendar.getInstance();
                    calendar.setTime(trenderList.get(0).getTimeStamp());
                    calendar.add(Calendar.MINUTE, -30);
                    date = calendar.getTime();
                }

                orderedTrender.addAll(trenderRepository.findByGameIdAndAndHorseIdAndTimeStampIsAfterOrderByTimeStampAsc(gameId, horseId, date));
            } else {
                orderedTrender.addAll(trenderRepository.findByGameIdAndAndHorseIdOrderByTimeStampAsc(gameId, horseId));
            }

            final int lastEntry = orderedTrender.size() - 1;
            final String raceId = orderedTrender.get(0).getRaceId();
            final String horseName = orderedTrender.get(0).getHorseName();
            final int horseNumber = orderedTrender.get(0).getHorseNumber();
            final long vOdds = orderedTrender.get(lastEntry).getVOdds() - orderedTrender.get(0).getVOdds();
            final long vDistribution = orderedTrender.get(lastEntry).getVDistribution() - orderedTrender.get(0).getVDistribution();
            final String timeStamp = LocalDate.now().toString();


            final float vOddsPercentage = orderedTrender.get(0).getVOdds() == 0 ?
                    1 : (float) orderedTrender.get(lastEntry).getVOdds() / orderedTrender.get(0).getVOdds();

            final float vDistributionPercentage = orderedTrender.get(0).getVDistribution() == 0 ?
                    1 : (float) orderedTrender.get(lastEntry).getVDistribution() / orderedTrender.get(0).getVDistribution();

            if (!trenderDtos.containsKey(raceId)) {
                trenderDtos.put(raceId, new ArrayList<>());
            }

            trenderDtos.get(raceId).add(new TrenderDto(horseId, horseName, horseNumber, vOdds, vDistribution, vOddsPercentage, vDistributionPercentage, timeStamp));

        });

        if (trenderDtos.isEmpty()) {
            trenderDtos.put("No data within timespan, adjust time so its before the race starts", new ArrayList<>());
        }
        return trenderDtos;
    }

    @Override
    public Map<String, List<TrenderMultisetDto>> getTrenderMultiset(String gameId, Optional<Date> startTime) {
        final List<Trender> trenderList = trenderRepository.findByGameIdOrderByTimeStampDesc(gameId);

        Map<String, List<TrenderMultisetDto>> trenderDtos = new HashMap<>();

        Calendar calendar = Calendar.getInstance();

        if (startTime.isPresent()) {
            calendar.setTime(startTime.get());
        }

        calendar.add(Calendar.HOUR_OF_DAY, -1);
        final Date oneHourAgo = calendar.getTime();
        calendar.add(Calendar.MINUTE, 30);
        final Date thirtyMinutesAgo = calendar.getTime();
        calendar.add(Calendar.MINUTE, 15);
        final Date fifteenMinutesAgo = calendar.getTime();

        final Set<Integer> horseIds = trenderList.stream().map(Trender::getHorseId).collect(Collectors.toSet());

        horseIds.forEach(horseId -> {
            List<Trender> orderedTrender = new ArrayList<>();
            orderedTrender.addAll(trenderRepository.findByGameIdAndAndHorseIdAndTimeStampIsAfterOrderByTimeStampAsc(gameId, horseId, oneHourAgo));

            final int lastEntry = orderedTrender.size() - 1;
            final int sixtyMinEntry = 0;
            final int thirtyMinEntry = IntStream.range(0, orderedTrender.size())
                    .filter(i -> orderedTrender.get(i).getTimeStamp().after(thirtyMinutesAgo))
                    .findFirst()
                    .orElse(-1);

            final int fifteenMinEntry = IntStream.range(0, orderedTrender.size())
                    .filter(i -> orderedTrender.get(i).getTimeStamp().after(fifteenMinutesAgo))
                    .findFirst()
                    .orElse(-1);


            final String raceId = orderedTrender.get(0).getRaceId();
            final String horseName = orderedTrender.get(0).getHorseName();
            final int horseNumber = orderedTrender.get(0).getHorseNumber();
            final long vDistribution0 = orderedTrender.get(lastEntry).getVDistribution();
            final long vDistribution60Change = vDistribution0 - orderedTrender.get(sixtyMinEntry).getVDistribution();
            final long vDistribution30Change = vDistribution0 - orderedTrender.get(thirtyMinEntry).getVDistribution();
            final long vDistribution15Change = vDistribution0 - orderedTrender.get(fifteenMinEntry).getVDistribution();
            final long vOdds0 = orderedTrender.get(lastEntry).getVOdds();

            final String timeStamp = LocalDate.now().toString();

            if (!trenderDtos.containsKey(raceId)) {
                trenderDtos.put(raceId, new ArrayList<>());
            }

            trenderDtos.get(raceId).add(new TrenderMultisetDto(horseId, horseName, horseNumber, vDistribution60Change, vDistribution30Change, vDistribution15Change,vDistribution0, vOdds0, timeStamp));

        });

        return trenderDtos;
    }

    @Override
    public Map<Integer, List<TrenderDto>> getAllHorsesTrender(String gameId, Optional<Date> afterDate) {
        log.log(Level.INFO, "getAllHorsesTrender(" + gameId + ", " + afterDate + ") called for gameId: " + gameId);
        final List<Trender> trenderList = trenderRepository.findByGameIdOrderByTimeStampDesc(gameId);
        Map<Integer, List<TrenderDto>> trenderDtos = new HashMap<>();

        Set<Integer> horseIds = trenderList.stream().map(Trender::getHorseId).collect(Collectors.toSet());

        horseIds.forEach(horseId -> {
            List<Trender> orderedTrender = new ArrayList<>();

            if (afterDate.isPresent()) {
                orderedTrender.addAll(trenderRepository.findByGameIdAndAndHorseIdAndTimeStampIsAfterOrderByTimeStampAsc(gameId, horseId, afterDate.get()));
            } else {
                orderedTrender.addAll(trenderRepository.findByGameIdAndAndHorseIdOrderByTimeStampAsc(gameId, horseId));
            }

            for (Trender trend : orderedTrender) {
                final String horseName = trend.getHorseName();
                final String timeStamp = trend.getTimeStamp().toString();
                final int horseNumber = trend.getHorseNumber();
                final long vOdds = trend.getVOdds();
                final long vDistribution = trend.getVDistribution();

                if (!trenderDtos.containsKey(horseId)) {
                    trenderDtos.put(horseId, new ArrayList<>());
                }

                trenderDtos.get(horseId).add(new TrenderDto(horseId, horseName, horseNumber, vOdds, vDistribution, timeStamp));

            }
        });

        return trenderDtos;
    }

    @Override
    public void removeYesterDaysTrends() {
        Calendar calendar = Calendar.getInstance();
        calendar.set(Calendar.HOUR_OF_DAY, 0);
        calendar.set(Calendar.MINUTE, 0);
        calendar.set(Calendar.SECOND, 0);
        calendar.set(Calendar.MILLISECOND, 0);
        Date startOfDay = calendar.getTime();
        log.info("removing trends before " + startOfDay);
        trenderRepository.deleteAllByTimeStampBefore(startOfDay);
    }
    private Integer generateHorseId(HorseDto horse) {
        return Math.abs(horse.getName().hashCode());
    }

}
