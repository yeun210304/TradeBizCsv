package com.TradeBizCsv.client;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Component;
import org.springframework.web.client.RestTemplate;

import com.fasterxml.jackson.databind.JsonNode;
import com.fasterxml.jackson.databind.ObjectMapper;

import lombok.extern.slf4j.Slf4j;

@Slf4j
@Component
public class TradeSellerApiClient {

    @Value("${api.tradeBiz.decodingKey}")
    private String apiDecodingKey;

    private final String apiUrl = "http://apis.data.go.kr/1130000/MllBsDtl_2Service";

    public String fetchData(String brno) {
        RestTemplate restTemplate = new RestTemplate();
        ObjectMapper objectMapper = new ObjectMapper();

        String crno = "";
        
        try {
            String url = makingUrl(brno);

            ResponseEntity<String> response = restTemplate.getForEntity(url, String.class);

            if (response.getStatusCode().is2xxSuccessful()) {
                JsonNode rootNode = objectMapper.readTree(response.getBody());
                JsonNode itemsNode = rootNode.get("items");
                crno = itemsNode.get(0).get("crno").asText();
            }
        }
        catch (Exception e) {
            log.error("error fetch 통신판매사업자 등록상세 제공 서비스 데이타={}", e.getMessage());
        }
        
        return crno;
    }
    
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
