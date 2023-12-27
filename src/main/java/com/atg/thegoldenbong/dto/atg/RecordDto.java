package com.atg.thegoldenbong.dto.atg;

import lombok.Data;

@Data
public class RecordDto {
    private String code;
    private String startMethod;
    private String distance;
    private TimeDto time;
}
