package com.TradeBizCsv.service;

import java.util.List;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;

import com.TradeBizCsv.client.PublicAddr;
import com.TradeBizCsv.client.TradeSellerApiClient;
import com.TradeBizCsv.domain.TradeBizInf;
import com.TradeBizCsv.repository.TradeBizRepository;
import com.TradeBizCsv.util.CsvReader;

import jakarta.transaction.Transactional;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;

@Slf4j
@Service
@RequiredArgsConstructor
public class TradeBizServiceImpl implements TradeBizService {

    private final TradeBizRepository tradeBizRepository;
    private final CsvReader csvReader;
    private final PublicAddr publicAddr;
    private final TradeSellerApiClient tradeSellerApiClient;
    
    @Override
    public List<String[]> loadCsv(MultipartFile file) {
        log.info("csv 파일 업로드 시작: {}", file.getOriginalFilename());
        List<String[]> csv = csvReader.readCsv(file);
        log.info("csv 파일 업로드 완료. 법인사업자 정보 총 {} 줄", csv.size());
        return csv;
    }

    @Override
    public String getCrno(String brno) {
        return tradeSellerApiClient.fetchData(brno).block();
    }

    @Override
    public String getAdmCd(String addr) {
        return publicAddr.getAdmCd(addr).block();
    }

    @Override
    @Transactional
    public void saveAllTradeBiz(List<TradeBizInf> tradeBizList) {
        tradeBizRepository.saveAll(tradeBizList);
        tradeBizRepository.flush();
        log.info("총 {} 개의 법인사업자 정보 저장 완료", tradeBizList.size());
    }

    public Page<TradeBizInf> getAllsavedInfs(Pageable pageable) {
        return tradeBizRepository.findAll(pageable);
    }
    
}
