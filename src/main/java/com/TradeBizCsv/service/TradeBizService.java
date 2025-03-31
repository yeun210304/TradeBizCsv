package com.TradeBizCsv.service;

import java.util.List;

import org.springframework.web.multipart.MultipartFile;

public interface TradeBizService {

    public List<String[]> loadCsv(MultipartFile file);

    public String searchCrno(String brno);
}
