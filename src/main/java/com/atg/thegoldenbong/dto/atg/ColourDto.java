package com.atg.thegoldenbong.dto.atg;

import lombok.Data;

@Data
public class ColourDto {

    private int id;
    private String code;
    private String text;
    private String engText;
    private boolean changed;
}
