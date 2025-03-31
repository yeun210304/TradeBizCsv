package com.TradeBizCsv.controller;

import java.util.List;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.multipart.MultipartFile;

import com.TradeBizCsv.domain.TradeBiz;
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

        for (String[] row : csv) {
            String brno = row[3].replaceAll("-", "");
            String crno = tradeBizService.searchCrno(brno).orElse("");
            log.info("brno={}, crno={}", brno, crno);

            //TODO : 생성된 데이터를 정리하여 저장하면 끝
            // new TradeBiz(brno, crno); 같은 식으로 만들어서 저장하면 됨
        }

        return "";
    }


}
