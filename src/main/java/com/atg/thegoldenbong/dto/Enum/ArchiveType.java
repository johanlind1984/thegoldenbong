package com.atg.thegoldenbong.dto.Enum;

public enum ArchiveType {
    ALL_WINNERS,

    ALL_V86_WINNERS,
    ALL_V75_WINNERS,
    ALL_V64_WINNERS,
    ALL_V65_WINNERS,
    ALL_G75_WINNERS,
    ALL_V3_WINNERS,
    ALL_V4_WINNERS,
    ALL_V5_WINNERS;

    public static ArchiveType ofGameId(String gameId) {
        if (gameId.contains("V86")) {
            return ALL_V86_WINNERS;
        }

        if (gameId.contains("V75")) {
            return ALL_V75_WINNERS;
        }

        if (gameId.contains("V65")) {
            return ALL_V65_WINNERS;
        }

        if (gameId.contains("V64")) {
            return ALL_V64_WINNERS;
        }

        if (gameId.contains("GS75")) {
            return ALL_G75_WINNERS;
        }

        if (gameId.contains("V3")) {
            return ALL_V3_WINNERS;
        }

        if (gameId.contains("V4")) {
            return ALL_V4_WINNERS;
        }

        if (gameId.contains("V5")) {
            return ALL_V5_WINNERS;
        }

        return null;
    }
}
