package com.atg.thegoldenbong.dto;

import lombok.Data;

@Data
public class TrenderMultisetDto {

    final Integer horseId;
    final String horseName;
    final Integer horseNumber;
    final float vDistribution60;
    final float vDistribution30;
    final float vDistribution15;
    boolean isTrendFlag;
    final String timeStamp;

    public TrenderMultisetDto(Integer horseId, String horseName, Integer horseNumber, float vDistribution60, float vDistribution30, float vDistribution15, final String timeStamp) {
        this.horseId = horseId;
        this.horseName = horseName;
        this.horseNumber = horseNumber;
        this.vDistribution60 = vDistribution60;
        this.vDistribution30 = vDistribution30;
        this.vDistribution15 = vDistribution15;

        if ((vDistribution15 / vDistribution60) > 0.5 && vDistribution15 > 20) {
            this.isTrendFlag = true;
        } else if (vDistribution60 < -100 && vDistribution15 > 0) {
            this.isTrendFlag = true;
        } else {
            this.isTrendFlag = false;
        }
        this.timeStamp = timeStamp;

    }

    public TrenderMultisetDto(Integer horseId, String horseName, Integer horseNumber, String timeStamp) {
        this.horseId = horseId;
        this.horseName = horseName;
        this.horseNumber = horseNumber;
        this.vDistribution60 = 0;
        this.vDistribution30 = 0;
        this.vDistribution15 = 0;
        this.isTrendFlag = false;
        this.timeStamp = timeStamp;

    }
}
