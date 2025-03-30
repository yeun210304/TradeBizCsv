package com.TradeBizCsv.client;

import java.util.Map;

import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Component;
import org.springframework.web.client.RestTemplate;

import com.fasterxml.jackson.databind.JsonNode;
import com.fasterxml.jackson.databind.ObjectMapper;

import lombok.extern.slf4j.Slf4j;

@Slf4j
@Component
public class TradeSellerApiClient {
    private final String apiUrl = "http://apis.data.go.kr/1130000/MllBsDtl_2Service";
    // private final String apiEncodingKey = "lHXPxamRGWYJrmUQB48W5cFRC4ItUhwYoRkcWrePrZqNZsTRMmKL%2F%2FJtPrPqzeh0%2Fn14QuDWF3mt6I4G5Hpf0Q%3D%3D";

    // 사용가능
    private final String apiDecodingKey = "lHXPxamRGWYJrmUQB48W5cFRC4ItUhwYoRkcWrePrZqNZsTRMmKL//JtPrPqzeh0/n14QuDWF3mt6I4G5Hpf0Q==";

    public String fetchData(String[] rowData) {
        RestTemplate restTemplate = new RestTemplate();
        ObjectMapper objectMapper = new ObjectMapper();
        
        try {
            // Map<String, String> response = restTemplate.getForObject(makingUrl(rowData[3].replaceAll("-", "")), Map.class);
            String url = makingUrl(rowData[3].replaceAll("-", ""));

            ResponseEntity<String> response = restTemplate.getForEntity(url, String.class);

            if (response.getStatusCode().is2xxSuccessful()) {
                JsonNode rootNode = objectMapper.readTree(response.getBody());
                JsonNode itemsNode = rootNode.get("items");
                String crno = itemsNode.get(0).get("crno").asText();
                
            }

            // log.info("response={}", value);
        }
        catch (Exception e) {
            log.error("error fetch 통신판매사업자 등록상세 제공 서비스 데이타={}", e.getMessage());
        }
        
        return null;
    }
    /**
    https://apis.data.go.kr/1130000/MllBsDtl_2Service/getMllBsInfoDetail_2?serviceKey=lHXPxamRGWYJrmUQB48W5cFRC4ItUhwYoRkcWrePrZqNZsTRMmKL%2F%2FJtPrPqzeh0%2Fn14QuDWF3mt6I4G5Hpf0Q%3D%3D&pageNo=1&numOfRows=10&resultType=json&brno=5348803361
     */
    public String makingUrl(String brno) {
        StringBuffer urlStrBuffer = new StringBuffer();
        urlStrBuffer.append(apiUrl)
                .append("/getMllBsInfoDetail_2?serviceKey=")
                .append(apiDecodingKey)
                .append("&pageNo=1")
                .append("&numOfRows=100")
                .append("&resultType=json")
                .append("&brno=")
                .append(brno);
        return urlStrBuffer.toString();
    }

}
