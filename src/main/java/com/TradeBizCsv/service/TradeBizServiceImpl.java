package com.TradeBizCsv.service;

import java.util.List;

import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;

import com.TradeBizCsv.client.TradeSellerApiClient;
import com.TradeBizCsv.util.CsvReader;

import lombok.RequiredArgsConstructor;

@Service
@RequiredArgsConstructor
public class TradeBizServiceImpl implements TradeBizService {

    public final CsvReader csvReader;
    public final TradeSellerApiClient tradeSellerApiClient;

    @Override
    public List<String[]> loadCsv(MultipartFile file) {
        return csvReader.readCsv(file);
    }

    @Override
    public List<String[]> searchCrno(List<String[]> data) {
        for (String[] row : data) {
            String searchCrno = tradeSellerApiClient.fetchData(row);
        }
        return null;
    }
    
}
