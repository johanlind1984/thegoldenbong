package com.atg.thegoldenbong.dto.atg;

import com.google.gson.annotations.SerializedName;
import lombok.Data;

import java.util.ArrayList;

@Data
public class GameDto {

    @SerializedName("id")
    private String id;

    @SerializedName("status")
    private String status;

    @SerializedName("pools")
    private PoolsDto pools;

    @SerializedName("races")
    private ArrayList<RacesDto> races = new ArrayList();

    @SerializedName("v64")
    private VDto v64;

    @SerializedName("v65")
    private VDto v65;

    @SerializedName("v75")
    private VDto v75;

    @SerializedName("v86")
    private VDto v86;

    @SerializedName("gs75")
    private VDto gs75;

    @SerializedName("version")
    private float version;

    @SerializedName("newBettingSystem")
    private boolean newBettingSystem;
}

