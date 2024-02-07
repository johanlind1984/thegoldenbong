package com.atg.thegoldenbong.dto;

import com.atg.thegoldenbong.util.TrendFlagUtil;
import lombok.Data;

@Data
public class TrenderMultisetDto {

    final Integer horseId;
    final String horseName;
    final Integer horseNumber;
    final float vDistribution60Change;
    final float vDistribution30Change;
    final float vDistribution15Change;
    final float vDistribution0;
    final float vOdds0;
    final float shouldBePlayed;
    boolean isTrendFlag;
    boolean isRedFlag;
    final String timeStamp;

    public TrenderMultisetDto(Integer horseId, String horseName, Integer horseNumber, float vDistribution60Change, float vDistribution30Change, float vDistribution15Change, float vDistribution0, float vOdds0, final String timeStamp) {
        this.horseId = horseId;
        this.horseName = horseName;
        this.horseNumber = horseNumber;
        this.vDistribution60Change = vDistribution60Change;
        this.vDistribution30Change = vDistribution30Change;
        this.vDistribution15Change = vDistribution15Change;
        this.vDistribution0 = vDistribution0;
        this.isTrendFlag = TrendFlagUtil.isTrendFlag(vDistribution60Change, vDistribution15Change, vDistribution0);
        this.isRedFlag = TrendFlagUtil.isRedFlag(vDistribution60Change, vDistribution15Change, vDistribution0);
        this.vOdds0 = vOdds0;
        this.shouldBePlayed = (100 / vOdds0) * 100;
        this.timeStamp = timeStamp;
    }

    public TrenderMultisetDto(Integer horseId, String horseName, Integer horseNumber, String timeStamp) {
        this.horseId = horseId;
        this.horseName = horseName;
        this.horseNumber = horseNumber;
        this.vDistribution60Change = 0;
        this.vDistribution30Change = 0;
        this.vDistribution15Change = 0;
        this.vDistribution0 = 0;
        this.vOdds0 = 0;
        this.shouldBePlayed = 0;
        this.isTrendFlag = false;
        this.isRedFlag = false;
        this.timeStamp = timeStamp;

    }
}
