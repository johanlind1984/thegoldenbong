package com.atg.thegoldenbong.service.impl;

import com.atg.thegoldenbong.service.CryptoTrendsService;
import org.springframework.stereotype.Service;
import org.springframework.web.util.UriComponentsBuilder;

import java.io.BufferedReader;
import java.io.InputStreamReader;
import java.net.HttpURLConnection;
import java.net.URL;
import java.util.Arrays;
import java.util.List;

@Service
public class CryptoTrendsServiceImpl implements CryptoTrendsService {

    final String BASE_URL = "https://trends.google.com/trends/api/widgetdata/multiline/csv";
    public String downloadReport(String keyword, String date, String geo, String geor, String graph, int sort, int scale, String sa) throws Exception {
        String time = "2023-10-30 2024-01-30";
        String reqParamValue = String.format("""
                {"time":"%s","resolution":"DAY","locale":"sv","comparisonItem":[{"geo":{},"complexKeywordsRestriction":{"keyword":[{"type":"BROAD","value":"%s"}]}}],"requestOptions":{"property":"","backend":"IZG","category":0},"userConfig":{"userType":"USER_TYPE_LEGIT_USER"}}
                """, time, keyword);

        String token = "APP6_UEAAAAAZbpQ4R6twu6iUvo_SlYN7JL4p9aK212d";
        int tz = -60;


        String urlString = UriComponentsBuilder.fromHttpUrl(BASE_URL)
                .queryParam("req", reqParamValue)
                .queryParam("token", token)
                .queryParam("tz", tz)
                .toUriString();

        URL url = new URL(urlString);

        HttpURLConnection connection = (HttpURLConnection) url.openConnection();
        connection.setRequestMethod("GET");

        int responseCode = connection.getResponseCode();
        if (responseCode != 200) {
            throw new Exception("Failed to download report: HTTP error code : " + responseCode);
        }

        BufferedReader in = new BufferedReader(new InputStreamReader(connection.getInputStream()));
        String inputLine;
        StringBuilder response = new StringBuilder();
        while ((inputLine = in.readLine()) != null) {
            response.append(inputLine);
        }
        in.close();

        return response.toString();
    }

    public List<List<String>> parseCsv(String rawCsv, boolean asList) {
        // Implement CSV parsing logic based on your requirements
        // This is a placeholder to indicate where parsing should be handled
        return Arrays.asList(Arrays.asList("Placeholder for CSV parsing"));
    }
}
