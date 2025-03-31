package com.TradeBizCsv.domain;

import lombok.Data;

@Data
public class TradeBiz {

    String prmmiMnno;   // 통신판매번호
    String bzmnNm;      // 상호
    String brno;        // 사업자등록번호
    String crno;        // 법인등록번호
    String admCd;       // 행정구역코드    

    public TradeBiz() {
    }
    

    
    
}
