package com.atg.thegoldenbong.dto;

import lombok.Data;

import java.util.Date;

@Data
public class NumberTrendDto {
    final Date timefetched;
    final Long vOdds;
    final Long distribution;
}
