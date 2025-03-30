package com.TradeBizCsv.domain;

import lombok.Data;

@Data
public class TradeBiz {

    String tradeBizNm;  // 통신판매번호
    String companyName; // 상호
    String bizRegistNm; // 사업자등록번호
    String crno;        // 법인등록번호
    // 행정구역코드    

    public TradeBiz() {
    }
    
}
