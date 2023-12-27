package com.atg.thegoldenbong.dto.atg;

import lombok.Data;

@Data
public class HorseDto {
    private int id;
    private String name;
    private int age;
    private String sex;
    private RecordDto record;
    private TrainerDto trainer;
    private ShoeSetDto shoes;
    private SulkyDto sulky;
    private int money;
    private String color;
    private HomeTrackDto homeTrack;
    private OwnerDto owner;
    private BreederDto breeder;
    private StatisticsDto statistics;
    private PedigreeDto pedigree;
}
