package com.TradeBizCsv.controller;

import java.util.List;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.multipart.MultipartFile;

import com.TradeBizCsv.service.TradeBizService;

import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;

@Slf4j
@Controller
@RequiredArgsConstructor
public class TradeBizController {

    private final TradeBizService tradeBizService;

    @GetMapping("/")
    public String welcomPage() {
        return "uploadform";
    }

    @PostMapping("/load-csv")
    public String loadCsvFile(@RequestParam("csvFile") MultipartFile file) {
        List<String[]> csv = tradeBizService.loadCsv(file);
        
        tradeBizService.searchCrno(csv);

        return "search-bubin-num";
    }


}
