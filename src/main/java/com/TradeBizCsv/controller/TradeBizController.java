package com.TradeBizCsv.controller;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;
import java.util.concurrent.CompletableFuture;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.multipart.MultipartFile;

import com.TradeBizCsv.domain.TradeBizInf;
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

        List<TradeBizInf> tradeBizInfLst = new ArrayList<>();

        List<String[]> csv = tradeBizService.loadCsv(file);
        
        for (String[] row : csv) {
            String prmmiMnno = row[0];                                      // 통신판매번호
            String bzmnNm = row[2];                                         // 상호
            String brno = row[3].replaceAll("-", "");       // 사업자등록번호

            CompletableFuture<String> crnoFuture = CompletableFuture.supplyAsync(() -> tradeBizService.getCrno(brno));
            String crno = crnoFuture.join();                                // 법인등록번호
            
            String addr = row[10].split("\\^")[0];
            CompletableFuture<String> admCdFuture = CompletableFuture.supplyAsync(() -> tradeBizService.getAdmCd(addr));
            String admCd = admCdFuture.join();                              // 행정구역코드

            tradeBizInfLst.add(new TradeBizInf(null, prmmiMnno, bzmnNm, brno, crno, admCd));
        }
        
        tradeBizService.saveAllTradeBiz(tradeBizInfLst);

        return "redirect:uploadform";
    }

}
