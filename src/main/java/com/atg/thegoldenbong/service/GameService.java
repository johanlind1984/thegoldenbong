package com.atg.thegoldenbong.service;

import com.atg.thegoldenbong.dto.atg.CalendarGamesDto;
import com.fasterxml.jackson.core.JsonProcessingException;

import java.util.List;

public interface GameService {

    CalendarGamesDto getTodayGames() throws JsonProcessingException;
    List<String> getTodaysGameIds() throws JsonProcessingException;
}
