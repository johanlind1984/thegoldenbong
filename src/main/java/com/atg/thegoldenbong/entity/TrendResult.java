package com.atg.thegoldenbong.entity;

import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.Id;
import lombok.Data;
import org.hibernate.annotations.CreationTimestamp;

@Data
@Entity
public class TrendResult {

    @Id
    @GeneratedValue
    final Integer id;

    @Column(name = "game_id")
    final String gameId;

    @Column(name = "horse_id")
    private int horseId;

    @Column(name = "horse_name")
    final String horseName;

    @Column(name = "horse_number")
    final Integer horseNumber;

    @Column(name = "vodds")
    final Long vOdds;

    @Column(name = "vdistribution")
    final Long distribution;

    @Column(name = "vodds_percentage")
    final float vOddsPercentage;

    @Column(name = "vdistribution_percentage")
    final float vDistributionPercentage;

    @Column(name = "timestamp")
    @CreationTimestamp
    final String timeStamp;

    @Column(name = "placement")
    final Integer placement;

}
