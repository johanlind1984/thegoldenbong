package com.atg.thegoldenbong.service;

import com.atg.thegoldenbong.dto.Enum.ArchiveType;
import com.atg.thegoldenbong.dto.TrenderDto;
import com.atg.thegoldenbong.dto.TrenderMultisetDto;
import com.atg.thegoldenbong.dto.atg.GameDto;
import com.atg.thegoldenbong.entity.TrendResult;
import com.atg.thegoldenbong.entity.Trender;
import org.springframework.stereotype.Service;

import java.text.ParseException;
import java.util.Date;
import java.util.List;
import java.util.Map;
import java.util.Optional;

@Service
public interface TrenderService {

    void saveDtoToDomain(final GameDto gameDto) throws ParseException;

    Map<String, List<TrenderDto>> getTrenderSummary(String gameId, Optional<Date> afterDate);

    Map<String, List<TrenderMultisetDto>> getTrenderMultiset(String gameId, Optional<Date> startTime);

    void removeYesterDaysTrends();

    Map<Integer, List<TrenderDto>> getAllHorsesTrender(String gameId, Optional<Date> afterDate);
}
