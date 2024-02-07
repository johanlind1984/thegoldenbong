package com.atg.thegoldenbong.batchjob;

import com.atg.thegoldenbong.dto.Enum.crypto.CryptoTicker;
import com.atg.thegoldenbong.service.CryptoTrendsService;
import com.google.gson.Gson;
import lombok.extern.log4j.Log4j2;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.stereotype.Component;
import org.springframework.web.client.RestTemplate;

import java.util.Arrays;
import java.util.List;

@Log4j2
@Component
public class CryptoTrendsDataFetchBatchJob {

    private final CryptoTrendsService cryptoTrendsService;
    private final List<String> cryptoTickers;
    final Gson gson;

    @Autowired
    public CryptoTrendsDataFetchBatchJob(RestTemplate restTemplate, final CryptoTrendsService cryptoTrendsService) {
        this.gson = new Gson();
        this.cryptoTickers = Arrays.stream(CryptoTicker.values()).map(Enum::name).toList();
        this.cryptoTrendsService = cryptoTrendsService;
    }

    @Scheduled(fixedRate = 14400000)
    public void fetchCryptoTrends() throws Exception {
/*        for (final String ticker : cryptoTickers) {
            String report = cryptoTrendsService.downloadReport(ticker, "all", "all", "all", "all_csv", 0, 0, "N");
            System.out.println(report);
        }*/
    }
}
