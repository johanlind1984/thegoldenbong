package com.atg.thegoldenbong.dto.atg;

import com.google.gson.annotations.SerializedName;
import lombok.Data;

import java.util.ArrayList;
import java.util.List;

@Data
public class CalendarGamesDto {

    @SerializedName("date")
    private final String date;

    @SerializedName("startTime")
    private final String startTime;

    @SerializedName("tracks")
    private final List<TrackDto> tacks;

    @SerializedName("games")
    private final Games games;

    public List<VDto> getAllGames() {
        List<VDto> allGames = new ArrayList<>();
        allGames.addAll(games.getV64());
        allGames.addAll(games.getV65());
        allGames.addAll(games.getV75());
        allGames.addAll(games.getV86());
        allGames.addAll(games.getGs75());
        allGames.addAll(games.getV3());
        allGames.addAll(games.getV4());
        allGames.addAll(games.getV5());
        allGames.addAll(games.getLd());
        allGames.addAll(games.getDd());
        return allGames;
    }

    @Data
    protected class Games {

        @SerializedName("V64")
        private List<VDto> v64;

        @SerializedName("V65")
        private List<VDto> v65;

        @SerializedName("V75")
        private List<VDto> v75;

        @SerializedName("V86")
        private List<VDto> v86;

        @SerializedName("GS75")
        private List<VDto> gs75;

        @SerializedName("V3")
        private List<VDto> v3;

        @SerializedName("V4")
        private List<VDto> v4;

        @SerializedName("V5")
        private List<VDto> v5;

        @SerializedName("ld")
        private List<VDto> ld;

        @SerializedName("dd")
        private List<VDto> dd;

        public List<VDto> getV64() {
            return v64 == null ? List.of() : v64;
        }

        public List<VDto> getV65() {
            return v65  == null ? List.of() : v65;
        }

        public List<VDto> getV75() {
            return v75  == null ? List.of() : v75;
        }

        public List<VDto> getV86() {
            return v86  == null ? List.of() : v86;
        }

        public List<VDto> getGs75() {
            return gs75  == null ? List.of() : gs75;
        }

        public List<VDto> getV3() {
            return v3  == null ? List.of() : v3;
        }

        public List<VDto> getV4() {
            return v4  == null ? List.of() : v4;
        }

        public List<VDto> getV5() {
            return v5  == null ? List.of() : v5;
        }

        public List<VDto> getLd() {
            return ld  == null ? List.of() : ld;
        }

        public List<VDto> getDd() {
            return dd  == null ? List.of() : dd;
        }
    }
}


