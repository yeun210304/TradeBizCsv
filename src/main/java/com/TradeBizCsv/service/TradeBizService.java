package com.TradeBizCsv.service;

import java.util.List;
import java.util.Optional;

import org.springframework.web.multipart.MultipartFile;

public interface TradeBizService {

    public List<String[]> loadCsv(MultipartFile file);

    public Optional<String> searchCrno(String brno);
}
