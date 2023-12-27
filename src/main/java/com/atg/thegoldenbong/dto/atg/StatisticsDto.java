package com.atg.thegoldenbong.dto.atg;

import lombok.Data;

import java.util.Map;

@Data
public class StatisticsDto {
    Map<String, YearStatisticsDto> yearStatistics;
}
