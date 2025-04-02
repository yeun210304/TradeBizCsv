package com.TradeBizCsv.client;

import java.util.Optional;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Component;
import org.springframework.web.reactive.function.client.WebClient;

import com.fasterxml.jackson.databind.JsonNode;

import lombok.extern.slf4j.Slf4j;
import reactor.core.publisher.Mono;

@Slf4j
@Component
public class TradeSellerApiClient {

    @Value("${api.tradeBiz.decodingKey}")
    private String apiDecodingKey;

    private final WebClient webClient;

    public TradeSellerApiClient(WebClient.Builder webClientBuilder) {
        this.webClient = webClientBuilder.baseUrl("http://apis.data.go.kr/1130000/MllBsDtl_2Service").build();
    }

    public Mono<String> fetchData(String brno) {
        String url = makeUrl(brno);

        return webClient.get()
                .uri(url)
                .retrieve()
                .bodyToMono(JsonNode.class)
                .map(param -> extracted(param))
                .onErrorResume(e -> {
                    log.error("법인등록번호 조회 실패 url: {}", webClient.toString() + url, e);
                    return Mono.just("");
                });
    }

    private String extracted(JsonNode rootNode) {
        return Optional.ofNullable(rootNode)
                        .filter(nodes -> nodes != null && nodes.has("items"))
                        .map(node -> node.get("items"))
                        .filter(itemsNode -> itemsNode != null && itemsNode.size() > 0)
                        .map(items -> items.get(0))
                        .filter(itemNode -> itemNode != null && itemNode.has("crno"))
                        .map(item -> item.get("crno"))
                        .map(JsonNode::asText)
                        .filter(admCd -> !admCd.isEmpty())
                        .orElse("");
    }
    
    public String makeUrl(String brno) {
        String resultType = "json";

        StringBuffer urlStrBuffer = new StringBuffer();
        urlStrBuffer.append("/getMllBsInfoDetail_2?serviceKey=")
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
