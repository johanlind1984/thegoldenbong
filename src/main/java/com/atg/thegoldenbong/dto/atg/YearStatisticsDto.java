package com.atg.thegoldenbong.dto.atg;

import lombok.Data;

@Data
public class YearStatisticsDto {
        private int starts;
        private int earnings;
        private PlacementDto placement;
        private int winPercentage;
}
