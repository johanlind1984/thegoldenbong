package com.atg.thegoldenbong.dto.atg;

import lombok.Data;

@Data
public class StartsDto
{
    private int number;
    private int postPosition;
    private int distance;
    private HorseDto horse;
    private DriverDto driver;
    private PoolsDto pools;

}
