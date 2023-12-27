package com.atg.thegoldenbong.dto.atg;

import lombok.Data;

@Data
public class PedigreeDto {
    private AncestorDto father;
    private AncestorDto mother;
    private AncestorDto grandfather;
    private AncestorDto grandmother;

}
