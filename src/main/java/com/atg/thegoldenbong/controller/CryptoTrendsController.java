package com.atg.thegoldenbong.controller;

import lombok.extern.log4j.Log4j2;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RestController;

@Log4j2
@RestController
public class CryptoTrendsController {
    @GetMapping("crypto/trends")
    public String getTrends() {

        return "";
    }

}
