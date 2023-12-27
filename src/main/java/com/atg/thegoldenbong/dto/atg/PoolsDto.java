package com.atg.thegoldenbong.dto.atg;

import com.google.gson.annotations.SerializedName;
import lombok.Data;

import java.util.Optional;

@Data
public class PoolsDto {

    @SerializedName("vinnare")
    private VinnareDto vinnare;

    @SerializedName("plats")
    private PlatsDto plats;

    @SerializedName("V64")
    private VDto v64;

    @SerializedName("V65")
    private VDto v65;

    @SerializedName("V75")
    private VDto v75;

    @SerializedName("V86")
    private VDto v86;

    @SerializedName("GS75")
    private VDto gs75;

    @SerializedName("V3")
    private VDto v3;

    @SerializedName("V4")
    private VDto v4;

    @SerializedName("V5")
    private VDto v5;

    private GameType gameType;

    public VDto getV() {
        if (Optional.ofNullable(v64).isPresent()) {
            return v64;
        } else if (Optional.ofNullable(v65).isPresent()) {
            return v65;
        } else if (Optional.ofNullable(v75).isPresent()) {
            return v75;
        } else if (Optional.ofNullable(v86).isPresent()) {
            return v86;
        } else if (Optional.ofNullable(gs75).isPresent()) {
            return gs75;
        } else if (Optional.ofNullable(v3).isPresent()) {
            return v3;
        }  else if (Optional.ofNullable(v4).isPresent()) {
            return v4;
        }  else if (Optional.ofNullable(v5).isPresent()) {
            return v5;
        }

        return null;
    }

    public void setV64(VDto v64) {
        gameType = GameType.V64;
        this.v64 = v64;
    }

    public void setV65(VDto v65) {
        gameType = GameType.V65;

        this.v65 = v65;
    }

    public void setV75(VDto v75) {
        gameType = GameType.V75;
        this.v75 = v75;
    }

    public void setV86(VDto v86) {
        gameType = GameType.V86;
        this.v86 = v86;
    }

    public void setGs75(VDto gs75) {
        gameType = GameType.GS75;
        this.gs75 = gs75;
    }

    private enum GameType {
        V64,
        V65,
        V75,
        V86,
        GS75
    }
}
