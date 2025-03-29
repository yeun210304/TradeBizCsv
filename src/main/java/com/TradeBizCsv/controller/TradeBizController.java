package com.TradeBizCsv.controller;

import java.util.Arrays;
import java.util.List;

import org.springframework.stereotype.Controller;
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

    @PostMapping("/load-csv")
    public String loadCsvFile(@RequestParam("csvFile") MultipartFile file) {
        if (file.isEmpty()) {
            log.error("error log={}", "file is empty");
        }
        List<String[]> csv = tradeBizService.loadCsv(file);
        
        for (String[] line : csv) {
            log.info("cell={}", Arrays.toString(line));
        }
        

        return "redirect:/";
    }


}
