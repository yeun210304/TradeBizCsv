package com.TradeBizCsv.domain;

import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Entity
@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
public class TradeBizInf {

    @Id @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;            // id

    @Column(name = "prmmiMnno", length = 20)
    private String prmmiMnno;   // 통신판매번호

    @Column(name = "bzmnNm", length = 100)
    private String bzmnNm;      // 상호

    @Column(name = "brno", length = 10)
    private String brno;        // 사업자등록번호

    @Column(name = "crno", length = 13)
    private String crno;        // 법인등록번호

    @Column(name = "admCd", length = 10)
    private String admCd;       // 행정구역코드

}
