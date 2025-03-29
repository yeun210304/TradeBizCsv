package com.TradeBizCsv.controller;

import org.springframework.stereotype.Controller;

import com.TradeBizCsv.service.TradeBizService;

import lombok.RequiredArgsConstructor;

@Controller
@RequiredArgsConstructor
public class TradeBizController {

    private final TradeBizService tradeBizService;


}
