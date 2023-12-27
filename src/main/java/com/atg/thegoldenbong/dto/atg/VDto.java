package com.atg.thegoldenbong.dto.atg;

import com.google.gson.annotations.SerializedName;
import lombok.Data;

import java.util.Date;
import java.util.List;

@Data
public class VDto {

    @SerializedName("type")
    private String type;

    @SerializedName("id")
    private String id;

    @SerializedName("status")
    private String status;

    @SerializedName("timestamp")
    private String timestamp;

    @SerializedName("startTime")
    private String startTime;

    @SerializedName("scheduledStartTime")
    private String scheduledStartTime;

    @SerializedName("turnover")
    private float turnover;

    @SerializedName("harry")
    private boolean harry;

    @SerializedName("systemcount")
    private float systemCount;

    @SerializedName("payouts")
    private PayoutsDto payouts;

    @SerializedName("jackpotamount")
    private float jackpotAmount;

    @SerializedName("betDistribution")
    private Long betDistribution;

    @SerializedName("races")
    private List<String> races;
}
