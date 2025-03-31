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

        for (String[] row : csv) {
            String prmmiMnno = row[0];                                      // 통신판매번호
            String bzmnNm = row[2];                                         // 상호
            
            String brno = row[3].replaceAll("-", "");       // 사업자등록번호
            String crno = tradeBizService.getCrno(brno);                    // 법인등록번호

            String addr = row[10].split("\\^")[0];
            String admCd = tradeBizService.getAdmCd(addr);                  // 행정구역코드    
            
            log.info("통신판매번호 prmmiMnno : {}, 상호 bzmnNm : {}, 사업자등록번호 brno : {}, 법인등록번호 crno : {}, 행정구역코드 admCd : {}", prmmiMnno, bzmnNm, brno, crno, admCd);
            
            //TODO : 생성된 데이터를 정리하여 저장하면 끝
            // new TradeBiz(brno, crno); 같은 식으로 만들어서 저장하면 됨
        }

        return "";
    }

}
