package com.atg.thegoldenbong.dto;

import lombok.Data;

@Data
public class TrenderDto {
    final Integer horseId;
    final String horseName;
    final Integer horseNumber;
    final Long vOdds;
    final Long distribution;
    final float vOddsPercentage;
    final float vDistributionPercentage;
    final String timeStamp;

    public TrenderDto(Integer horseId, String horseName, Integer horseNumber, Long vOdds, Long distribution, float vOddsPercentage, float vDistributionPercentage, final String timeStamp) {
        this.horseId = horseId;
        this.horseName = horseName;
        this.horseNumber = horseNumber;
        this.vOdds = vOdds;
        this.distribution = distribution;
        this.vOddsPercentage = vOddsPercentage;
        this.vDistributionPercentage = vDistributionPercentage;
        this.timeStamp = timeStamp;

    }

    public TrenderDto(Integer horseId, String horseName, Integer horseNumber, Long vOdds, Long distribution, String timeStamp) {
        this.horseId = horseId;
        this.horseName = horseName;
        this.horseNumber = horseNumber;
        this.vOdds = vOdds;
        this.distribution = distribution;
        this.vDistributionPercentage = 0;
        this.vOddsPercentage = 0;
        this.timeStamp = timeStamp;

    }
}
