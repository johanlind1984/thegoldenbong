package com.atg.thegoldenbong.service.impl;

import com.atg.thegoldenbong.entity.TrendResult;
import com.atg.thegoldenbong.repository.TrenderResultRepository;
import com.atg.thegoldenbong.service.TrendResultService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class TrendResultServiceImpl implements TrendResultService {

    TrenderResultRepository trenderResultRepository;

    @Autowired
    public TrendResultServiceImpl(TrenderResultRepository trenderResultRepository) {
        this.trenderResultRepository = trenderResultRepository;
    }

    @Override
    public TrendResult findByGameIdAndAndHorseId(String gameId, Integer horseId) {
        return trenderResultRepository.findByGameIdAndAndHorseId(gameId, horseId);
    }

    @Override
    public List<TrendResult> findByGame(String gameId) {
        return trenderResultRepository.findByGameId(gameId);
    }
}
