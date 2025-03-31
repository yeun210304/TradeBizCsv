package com.TradeBizCsv.service;

import static org.junit.jupiter.api.Assertions.*;

import java.util.Optional;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import com.TradeBizCsv.client.TradeSellerApiClient;
import com.TradeBizCsv.util.CsvReader;

import lombok.extern.slf4j.Slf4j;

@Slf4j
public class TradeBizServiceImplTest {

    TradeBizService tradeBizService;
    TradeSellerApiClient tradeSellerApiClient;
    CsvReader csvReader;

    @BeforeEach
    void setUp() {
        csvReader = new CsvReader();
        tradeSellerApiClient = new TradeSellerApiClient();
        tradeBizService = new TradeBizServiceImpl(csvReader, tradeSellerApiClient);
    }

    @Test
    void searchCrno() {
        //given
        String brno = "5348803361";
        Optional<String> expectCrno = Optional.of(new String("1101110919193"));
        tradeSellerApiClient.setApiDecodingKey("lHXPxamRGWYJrmUQB48W5cFRC4ItUhwYoRkcWrePrZqNZsTRMmKL//JtPrPqzeh0/n14QuDWF3mt6I4G5Hpf0Q==");

        //when
        String crno = tradeBizService.searchCrno(brno).orElse("");

        //then
        assertEquals(crno, expectCrno.get());
    }
}
