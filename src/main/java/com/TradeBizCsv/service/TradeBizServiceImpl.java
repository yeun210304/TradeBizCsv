package com.TradeBizCsv.service;

import java.util.List;

import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;

import com.TradeBizCsv.util.CsvReader;

import lombok.RequiredArgsConstructor;

@Service
@RequiredArgsConstructor
public class TradeBizServiceImpl implements TradeBizService {

    public final CsvReader csvReader;

    @Override
    public List<String[]> loadCsv(MultipartFile file) {
        return csvReader.readCsv(file);
    }
    
}
