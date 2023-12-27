package com.atg.thegoldenbong.dto.atg;

import lombok.Data;

@Data
public class DriverDto {
    private int id;
    private String firstName;
    private String lastName;
    private String shortName;
    private String location;
    private int birth;
    private HomeTrackDto homeTrack;
    private String license;
    private String silks;
    private StatisticsDto statistics;
}
