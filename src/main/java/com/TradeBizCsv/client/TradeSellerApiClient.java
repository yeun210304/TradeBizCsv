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
public class TradeSellerApiClient {

    @Value("${api.tradeBiz.decodingKey}")
    private String apiDecodingKey;

    public Optional<String> fetchData(String brno) {
        
        RestTemplate restTemplate = new RestTemplate();
        ObjectMapper objectMapper = new ObjectMapper();

        Optional<String> crno = Optional.empty();
        String url = makeUrl(brno);
        
        try {
            ResponseEntity<String> response = restTemplate.getForEntity(url, String.class);

            if (response.getStatusCode().is2xxSuccessful()) {
                JsonNode rootNode = objectMapper.readTree(response.getBody());

                crno = extracted(rootNode);
            }
        }
        catch (JsonProcessingException e) {
            log.error("error with json parsing: {}", e.getMessage());
        }
        catch (CompletionException e) {
            log.error("법인등록번호 조회 실패 url: {}", url);
        }
        
        return crno;
    }

    private Optional<String> extracted(JsonNode rootNode) {
        return Optional.ofNullable(rootNode.get("items"))
                        .filter(itemsNode -> itemsNode != null && itemsNode.size() > 0)
                        .map(items -> items.get(0))
                        .filter(itemNode -> itemNode != null && itemNode.size() > 0 && itemNode.has("crno"))
                        .map(item -> item.get("crno"))
                        .map(JsonNode::asText)
                        .filter(admCd -> !admCd.isEmpty());
    }
    
    public String makeUrl(String brno) {
        String apiUrl = "http://apis.data.go.kr/1130000/MllBsDtl_2Service";
        String resultType = "json";

        StringBuffer urlStrBuffer = new StringBuffer();
        urlStrBuffer.append(apiUrl)
                .append("/getMllBsInfoDetail_2?serviceKey=")
                .append(apiDecodingKey)
                .append("&pageNo=1")
                .append("&numOfRows=100")
                .append("&resultType=")
                .append(resultType)
                .append("&brno=")
                .append(brno);
        return urlStrBuffer.toString();
    }

    public void setApiDecodingKey(String apiDecodingKey) {
        this.apiDecodingKey = apiDecodingKey;
    }
}
