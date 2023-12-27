package com.atg.thegoldenbong.dto.atg;

import com.google.gson.annotations.SerializedName;
import lombok.Data;

@Data
public class TrainerDto {

    @SerializedName("id")
    private int id;

    @SerializedName("firstName")
    private String firstName;

    @SerializedName("lastName")
    private String lastName;

    @SerializedName("shortName")
    private String shortName;

    @SerializedName("location")
    private String location;

    @SerializedName("birth")
    private int birth;

    @SerializedName("hometrack")
    private HomeTrackDto homeTrack;

    @SerializedName("licence")
    private String license;

    @SerializedName("silks")
    private String silks;

    @SerializedName("statistics")
    private StatisticsDto statistics;
}
