package com.atg.thegoldenbong.entity;

import jakarta.persistence.*;
import lombok.Data;

import java.util.Date;
import java.util.List;

//@Entity
@Data
public class Race {

  /*  @Id
    @GeneratedValue
    private Long id;

    @Column(name = "foreign_id")
    private String foreignId;

    @Column(name = "name")
    private String name;

    @Column(name = "date")
    private Date date;

    @Column(name = "number")
    private int number;

    @Column(name = "distance")
    private int distance;

    @Column(name = "start_method")
    private String startMethod;

    @Column(name = "start_time")
    private Date startTime;

    @Column(name = "scheduled_start_time")
    private Date scheduledStartTime;

    @Column(name = "prize")
    private String prize;

    @OneToMany(mappedBy = "race")
    private List<Term> terms;

    @Column(name = "sport")
    private String sport;

    @ManyToOne
    private Track track;

    @Column(name = "status")
    private String status;

    @OneToOne(mappedBy = "race", cascade = CascadeType.ALL)
    @PrimaryKeyJoinColumn
    private Pool pool;

    @OneToMany(mappedBy = "race")
    private List<Starts> starts;*/
}
