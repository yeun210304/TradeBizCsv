package com.TradeBizCsv.controller;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import java.util.concurrent.CompletableFuture;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.multipart.MultipartFile;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;

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
        return "mainPage";
    }

    @PostMapping("/saveTraceBizInf")
    public String loadCsvFile(@RequestParam("csvFile") MultipartFile file, RedirectAttributes redirectAttributes) {

        List<String[]> csv = tradeBizService.loadCsv(file);

        List<CompletableFuture<TradeBizInf>> futures = new ArrayList<>();
        
        for (String[] row : csv) {
            String prmmiMnno = row[0];                                      // 통신판매번호
            String bzmnNm = row[2];                                         // 상호
            String brno = row[3].replaceAll("-", "");       // 사업자등록번호
            String addr = row[10].split("\\^")[0]; 

            // 법인등록번호
            CompletableFuture<String> crnoFuture = CompletableFuture.supplyAsync(() -> tradeBizService.getCrno(brno));
            // 행정구역코드
            CompletableFuture<String> admCdFuture = CompletableFuture.supplyAsync(() -> tradeBizService.getAdmCd(addr));

            CompletableFuture<TradeBizInf> tradeBizFuture = crnoFuture.thenCombine(admCdFuture, 
            (crno, admCd) -> new TradeBizInf(null, prmmiMnno, bzmnNm, brno, crno, admCd));

            futures.add(tradeBizFuture);
        }

        List<TradeBizInf> tradeBizInfLst = futures.stream()
                .map(CompletableFuture::join)
                .toList();
        
        tradeBizService.saveAllTradeBiz(tradeBizInfLst);

        redirectAttributes.addAttribute("resMsg", "총 " + tradeBizInfLst.size() + " 건이 저장되었습니다.");
        return "redirect:/";
    }

}
