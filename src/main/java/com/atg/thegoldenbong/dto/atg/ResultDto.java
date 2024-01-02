package com.atg.thegoldenbong.dto.atg;

import com.google.gson.annotations.SerializedName;
import lombok.Data;

import java.util.List;

@Data
public class ResultDto {

    @SerializedName("systems")
    private String systems;

    @SerializedName("winners")
    private List<Integer> winners;
}
