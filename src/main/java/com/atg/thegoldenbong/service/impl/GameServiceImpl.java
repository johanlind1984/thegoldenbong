package com.atg.thegoldenbong.service.impl;

import com.atg.thegoldenbong.dto.atg.CalendarGamesDto;
import com.atg.thegoldenbong.dto.atg.VDto;
import com.atg.thegoldenbong.service.GameService;
import com.fasterxml.jackson.core.JsonProcessingException;
import com.google.gson.Gson;
import lombok.extern.log4j.Log4j2;
import org.apache.logging.log4j.Level;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;
import org.springframework.web.client.RestTemplate;

import java.time.LocalDate;
import java.time.format.DateTimeFormatter;
import java.util.ArrayList;
import java.util.List;

@Log4j2
@Service
public class GameServiceImpl implements GameService {

    private final String BASE_URI = "https://www.atg.se/services/racinginfo/v1/api/";
    final RestTemplate restTemplate = new RestTemplate();
    final Gson gson = new Gson();

    @Override
    public List<String> getTodaysGameIds() throws JsonProcessingException {
        return getTodayGames().getAllGames().stream().map(VDto::getId).toList();
    }

    @Override
    public CalendarGamesDto getTodayGames() throws JsonProcessingException {
        log.log(Level.INFO, "Getting todays games");

        final LocalDate dateObj = LocalDate.now();
        final DateTimeFormatter formatter = DateTimeFormatter.ofPattern("yyyy-MM-dd");
        String date = dateObj.format(formatter);
        final String uri = BASE_URI + "calendar/day/" + date;
        final List<String> keys = new ArrayList<>();

        final ResponseEntity<String> response = restTemplate.getForEntity(uri, String.class);
        final CalendarGamesDto calendarGamesDto = gson.fromJson(response.getBody(), CalendarGamesDto.class);

        return calendarGamesDto;
    }
}
