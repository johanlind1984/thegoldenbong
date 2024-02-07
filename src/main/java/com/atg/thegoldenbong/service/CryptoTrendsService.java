package com.atg.thegoldenbong.service;

import java.util.List;

public interface CryptoTrendsService {
    public String downloadReport(String keywords, String date, String geo, String geor, String graph, int sort, int scale, String sa) throws Exception;
}