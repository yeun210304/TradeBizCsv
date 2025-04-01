package com.TradeBizCsv.service;

import java.util.List;

import org.springframework.web.multipart.MultipartFile;

import com.TradeBizCsv.domain.TradeBizInf;

public interface TradeBizService {

    public List<String[]> loadCsv(MultipartFile file);

    public String getCrno(String brno);

    public String getAdmCd(String addr);

    public TradeBizInf saveTradeBiz(TradeBizInf tradeBizInf);
    
}
