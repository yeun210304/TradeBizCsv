package com.TradeBizCsv.service;

import java.util.List;

import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;

import com.TradeBizCsv.client.PublicAddr;
import com.TradeBizCsv.client.TradeSellerApiClient;
import com.TradeBizCsv.domain.TradeBizInf;
import com.TradeBizCsv.repository.TradeBizRepository;
import com.TradeBizCsv.util.CsvReader;

import lombok.RequiredArgsConstructor;

@Service
@RequiredArgsConstructor
public class TradeBizServiceImpl implements TradeBizService {

    private final TradeBizRepository tradeBizRepository;
    private final CsvReader csvReader;
    private final PublicAddr publicAddr;
    private final TradeSellerApiClient tradeSellerApiClient;

    @Override
    public List<String[]> loadCsv(MultipartFile file) {
        return csvReader.readCsv(file);
    }

    @Override
    public String getCrno(String brno) {
        return tradeSellerApiClient.fetchData(brno).orElse("");
    }

    @Override
    public String getAdmCd(String addr) {
        return publicAddr.getAdmCd(addr).orElse("");
    }

    @Override
    public TradeBizInf saveTradeBiz(TradeBizInf tradeBizInf) {
        return tradeBizRepository.save(tradeBizInf);
    }
    
}
