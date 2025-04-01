package com.TradeBizCsv.service;

import static org.junit.jupiter.api.Assertions.*;

import java.util.Optional;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;

import com.TradeBizCsv.client.PublicAddr;
import com.TradeBizCsv.client.TradeSellerApiClient;
import com.TradeBizCsv.repository.TradeBizRepository;
import com.TradeBizCsv.util.CsvReader;

import lombok.extern.slf4j.Slf4j;

@Slf4j
public class TradeBizServiceImplTest {

    @InjectMocks
    TradeBizService tradeBizService;

    @Mock
    TradeBizRepository tradeBizRepository;

    @Mock
    TradeSellerApiClient tradeSellerApiClient;

    @Mock
    CsvReader csvReader;

    @Mock
    PublicAddr publicAddr;

    @BeforeEach
    void setUp() {
        MockitoAnnotations.openMocks(this);
    }

    @Test
    void searchCrno() {
        //given
        String brno = "5348803361";
        Optional<String> expectCrno = Optional.of(new String("1101110919193"));
        tradeSellerApiClient.setApiDecodingKey("lHXPxamRGWYJrmUQB48W5cFRC4ItUhwYoRkcWrePrZqNZsTRMmKL//JtPrPqzeh0/n14QuDWF3mt6I4G5Hpf0Q==");

        //when
        String crno = tradeBizService.getCrno(brno);

        //then
        assertEquals(crno, expectCrno.get());
    }
}
