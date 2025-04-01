package com.TradeBizCsv.domain;

import jakarta.persistence.Column;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import lombok.Getter;
import lombok.Setter;
import lombok.RequiredArgsConstructor;

@Getter
@Setter
@RequiredArgsConstructor
public class TradeBizInf {

    @Id @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;            // id

    @Column(name = "prmmiMnno")
    private String prmmiMnno;   // 통신판매번호

    @Column(name = "bzmnNm")
    private String bzmnNm;      // 상호
 
    @Column(name = "brno")
    private String brno;        // 사업자등록번호

    @Column(name = "crno")
    private String crno;        // 법인등록번호

    @Column(name = "admCd")
    private String admCd;       // 행정구역코드

}
