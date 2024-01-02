package com.atg.thegoldenbong.dto.atg;

import com.google.gson.annotations.SerializedName;
import lombok.Data;
import java.util.List;

@Data
public class RacesDto {
    @SerializedName("id")
    private String id;

    @SerializedName("name")
    private String name;

    @SerializedName("date")
    private String date;

    @SerializedName("number")
    private int number;

    @SerializedName("distance")
    private int distance;

    @SerializedName("startMethod")
    private String startMethod;

    @SerializedName("startTime")
    private String startTime;

    @SerializedName("scheduledStartTime")
    private String scheduledStartTime;

    @SerializedName("prize")
    private String prize;

    @SerializedName("terms")
    private List<String> terms;


    //private String sport;

//    @SerializedName("track")
//    private TrackDto track;
    @SerializedName("status")
    private String status;

    @SerializedName("pools")
    private PoolsDto pools;

    @SerializedName("starts")
    private List<StartsDto> starts;

    @SerializedName("mergedPools")
    private List<MergedPoolsDto> mergedPools;

    @SerializedName("winners")
    private List<Integer> winners;
}
