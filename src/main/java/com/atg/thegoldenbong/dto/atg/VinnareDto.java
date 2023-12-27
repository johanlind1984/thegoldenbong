package com.atg.thegoldenbong.dto.atg;

import lombok.Data;

@Data
public class VinnareDto {
    private String type;
    private String id;
    private Long odds;
    private VDto v64;
    private VDto v65;
    private VDto v75;
    private VDto v86;
    private VDto gs75;
    private String status;
    private String timestamp;
    private long turnover;
}
