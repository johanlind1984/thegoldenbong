package com.atg.thegoldenbong.entity;

import jakarta.persistence.*;
import lombok.Data;
import org.hibernate.annotations.CreationTimestamp;

import java.util.Date;

@Data
@Entity
public class Trender {

    @Id
    @GeneratedValue
    private Long id;

    @Column(name = "race_id")
    private String raceId;

    @Column(name = "game_id")
    private String gameId;

    @Column(name = "race_number")
    private int raceNumber;

    @Column(name = "horse_number")
    private int horseNumber;

    @Column(name = "horse_name")
    private String horseName;

    @Column(name = "horse_id")
    private int horseId;

//    @Column(name = "post_position")
//    private int postPosition;

    @Column(name = "vodds")
    private long vOdds;

    @Column(name = "vdistribution")
    private long vDistribution;

    @Column(name = "timestamp")
    @CreationTimestamp
    private Date timeStamp;

}
