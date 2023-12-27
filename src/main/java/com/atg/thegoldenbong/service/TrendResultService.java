package com.atg.thegoldenbong.service;

import com.atg.thegoldenbong.dto.atg.GameDto;
import com.atg.thegoldenbong.entity.TrendResult;
import com.atg.thegoldenbong.entity.Trender;
import org.springframework.stereotype.Service;

import java.util.Date;
import java.util.List;

@Service
public interface TrendResultService {
    TrendResult findByGameIdAndAndHorseId(final String gameId, final Integer horseId);
    List<TrendResult> findByGame(final String gameId);

}
