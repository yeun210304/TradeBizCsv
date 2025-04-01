package com.TradeBizCsv.client;

import java.util.Optional;
import java.util.concurrent.CompletionException;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Component;
import org.springframework.web.client.RestTemplate;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.JsonNode;
import com.fasterxml.jackson.databind.ObjectMapper;

import lombok.extern.slf4j.Slf4j;

@Slf4j
@Component
public class PublicAddr {

    @Value("${api.publicAddr.key}")
    private String confmKey;

    public Optional<String> getAdmCd(String keyword) {

        RestTemplate restTemplate = new RestTemplate();
        ObjectMapper objectMapper = new ObjectMapper();

        Optional<String> prmmiMnno = Optional.empty();
        String url = makeUrl(keyword);

        try {
            ResponseEntity<String> response = restTemplate.getForEntity(url, String.class);
            
            if (response.getStatusCode().is2xxSuccessful()) {
                JsonNode rootNode = objectMapper.readTree(response.getBody());

                prmmiMnno = extracted(rootNode);
            }
        }
        catch (JsonProcessingException e) {
            log.error("error with json parsing: {}", e.getMessage());
        }
        catch (CompletionException e) {
            log.error("행정구역코드 조회 실패 url: {}", url);
        }

        return prmmiMnno;
    }

    private Optional<String> extracted(JsonNode rootNode) {
        return Optional.ofNullable(rootNode.get("results"))
                        .map(res -> res.get("juso"))
                        .filter(jusoNode -> jusoNode != null && jusoNode.size() > 0)
                        .map(jusoNode -> jusoNode.get(0))
                        .filter(itemNode -> itemNode != null && itemNode.has("admCd"))
                        .map(itemNode -> itemNode.get("admCd").asText())
                        .filter(admCd -> !admCd.isEmpty());
    }

    private String makeUrl(String keyword) {
        String currentPage = "1";
        String countPerPage = "10";
        String resultType = "json";

        StringBuffer urlStrBuffer = new StringBuffer();
        
        urlStrBuffer.append("https://business.juso.go.kr/addrlink/addrLinkApi.do?currentPage=")
            .append(currentPage)
            .append("&countPerPage=")
            .append(countPerPage)
            .append("&keyword=")
            .append(keyword)
            .append("&confmKey=")
            .append(confmKey)
            .append("&resultType=")
            .append(resultType);
        
        return urlStrBuffer.toString();
    }
}
