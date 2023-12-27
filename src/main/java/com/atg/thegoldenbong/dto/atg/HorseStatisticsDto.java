package com.atg.thegoldenbong.dto.atg;

import lombok.Data;

import java.util.List;

@Data
public class HorseStatisticsDto {

    private int id;
    private int horseId;
    private YearStatisticsDto years;
    private List<YearStatisticsDto> life;
    private LastFiveStartsDto lastFiveStarts;
}
