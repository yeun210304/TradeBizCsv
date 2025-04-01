package com.TradeBizCsv.repository;

import org.springframework.data.jpa.repository.JpaRepository;

import com.TradeBizCsv.domain.TradeBizInf;

public interface TradeBizRepository extends JpaRepository<TradeBizInf, Long> {
    
}
