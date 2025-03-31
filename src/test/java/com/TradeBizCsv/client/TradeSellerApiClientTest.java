package com.TradeBizCsv.client;

import static org.junit.jupiter.api.Assertions.*;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.InjectMocks;
import org.springframework.http.ResponseEntity;

import lombok.extern.slf4j.Slf4j;

@Slf4j
public class TradeSellerApiClientTest {

    @InjectMocks
    private TradeSellerApiClient tradeSellerApiClient;

    @BeforeEach
    void setUp() {
        tradeSellerApiClient = new TradeSellerApiClient();
        tradeSellerApiClient.setApiDecodingKey("lHXPxamRGWYJrmUQB48W5cFRC4ItUhwYoRkcWrePrZqNZsTRMmKL//JtPrPqzeh0/n14QuDWF3mt6I4G5Hpf0Q==");
    }

    @Test
    void fetchData() {
        //given
        String brno = "5348803361";
        ResponseEntity<String> response = ResponseEntity.ok("1101110919193");

        //when
        String crno = tradeSellerApiClient.fetchData(brno).orElse("");
        log.info("crno={}", crno);

        //then
        assertEquals(response.getBody().toString(), crno);
    }
}
