package com.atg.thegoldenbong.dto.atg;

import lombok.Data;

@Data
public class ShoeSetDto {
    private boolean reported;
    private ShoeDto front;
    private ShoeDto back;

}
