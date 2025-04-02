package com.TradeBizCsv.service;

import static org.junit.jupiter.api.Assertions.*;
import static org.assertj.core.api.Assertions.*;

import java.util.Arrays;
import java.util.List;
import java.util.Optional;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;

import com.TradeBizCsv.client.PublicAddr;
import com.TradeBizCsv.client.TradeSellerApiClient;
import com.TradeBizCsv.domain.TradeBizInf;
import com.TradeBizCsv.repository.TradeBizRepository;
import com.TradeBizCsv.util.CsvReader;

@SpringBootTest
public class TradeBizServiceImplTest {

    private TradeBizServiceImpl tradeBizService;

    @Autowired
    private TradeBizRepository tradeBizRepository;

    private CsvReader csvReader;

    @Autowired
    private PublicAddr publicAddr;

    @Autowired
    private TradeSellerApiClient tradeSellerApiClient;
    
    @BeforeEach
    void setUp() {
        csvReader = new CsvReader();
        
        tradeBizService = new TradeBizServiceImpl(
            tradeBizRepository, csvReader, publicAddr, tradeSellerApiClient
        );
    }

    @Test
    void getCrno() {
        //given
        String brno = "5348803361";
        Optional<String> expectCrno = Optional.of(new String("1101110919193"));
        tradeSellerApiClient.setApiDecodingKey("lHXPxamRGWYJrmUQB48W5cFRC4ItUhwYoRkcWrePrZqNZsTRMmKL//JtPrPqzeh0/n14QuDWF3mt6I4G5Hpf0Q==");

        //when
        String crno = tradeBizService.getCrno(brno);

        //then
        assertEquals(crno, expectCrno.get());
    }

    @Test
    void saveAllTradeBiz() {
        //given
        List<TradeBizInf> lst = Arrays.asList(
            new TradeBizInf(null, "2025-서울광진-0419", "주식회사 커스텀씨앤씨", "5348803361", "1101110919193","1121510900"));

        // when
        tradeBizService.saveAllTradeBiz(lst);
        Optional<TradeBizInf> res = tradeBizRepository.findById(0L);

        // then
        assertThat(res.get()).isEqualTo(lst.get(0));
    }

}
