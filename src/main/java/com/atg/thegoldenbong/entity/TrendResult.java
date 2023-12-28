package com.atg.thegoldenbong.entity;

import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.Id;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.hibernate.annotations.CreationTimestamp;

@Data
@NoArgsConstructor
@AllArgsConstructor
@Entity
public class TrendResult {

    @Id
    @GeneratedValue
    private Integer id;

    @Column(name = "game_id")
    private String gameId;

    @Column(name = "race_id")
    private String raceId;

    @Column(name = "race_number")
    private int raceNumber;

    @Column(name = "horse_id")
    private int horseId;

    @Column(name = "horse_name")
    private String horseName;

    @Column(name = "horse_number")
    private Integer horseNumber;

    @Column(name = "vodds60")
    private Long vOdds60;

    @Column(name = "vodds30")
    private Long vOdds30;

    @Column(name = "vodds15")
    private Long vOdds15;

    @Column(name = "vodds0")
    private Long vOdds0;

    @Column(name = "vdistribution60")
    private Long distribution60;

    @Column(name = "vdistribution30")
    private Long distribution30;

    @Column(name = "vdistribution15")
    private Long distribution15;

    @Column(name = "vdistribution0")
    private Long distribution0;

    @Column(name = "placement")
    private Integer placement;

    @Column(name = "timestamp")
    @CreationTimestamp
    private String timeStamp;

}
