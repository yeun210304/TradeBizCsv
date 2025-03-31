package com.TradeBizCsv.client;

import static org.junit.jupiter.api.Assertions.*;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.http.ResponseEntity;
import org.springframework.web.client.RestTemplate;

import lombok.extern.slf4j.Slf4j;

@Slf4j
@SpringBootTest
public class TradeSellerApiClientTest {

    @Mock
    private RestTemplate restTemplate;
    
    @InjectMocks
    private TradeSellerApiClient tradeSellerApiClient;

    @BeforeEach
    void setUp() {
        tradeSellerApiClient = new TradeSellerApiClient();
        tradeSellerApiClient.setApiDecodingKeyForTest("lHXPxamRGWYJrmUQB48W5cFRC4ItUhwYoRkcWrePrZqNZsTRMmKL//JtPrPqzeh0/n14QuDWF3mt6I4G5Hpf0Q==");
    }

    @Test
    void testFetchData() {
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
