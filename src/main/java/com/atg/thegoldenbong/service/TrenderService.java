package com.atg.thegoldenbong.service;

import com.atg.thegoldenbong.dto.TrenderDto;
import com.atg.thegoldenbong.dto.TrenderMultisetDto;
import com.atg.thegoldenbong.dto.atg.GameDto;
import com.atg.thegoldenbong.entity.Trender;
import org.springframework.stereotype.Service;

import java.util.Date;
import java.util.List;
import java.util.Map;
import java.util.Optional;

@Service
public interface TrenderService {

    void saveDtoToDomain(final GameDto gameDto);

    Map<String, List<TrenderDto>> getTrenderSummary(String gameId, Optional<Date> afterDate);

    Map<String, List<TrenderMultisetDto>> getTrenderMultiset(String gameId);

    void deleteTrendsBeforeDate(final Date date);

    List<Trender> findByGameIdAndAndHorseIdAndTimeStampIsAfterOrderByTimeStampAsc(final String gameId, final Integer horseId, final Date afterDate);

    Map<Integer, List<TrenderDto>> getAllHorsesTrender(String gameId, Optional<Date> afterDate);
}
