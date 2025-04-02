package com.TradeBizCsv.client;

import java.util.Optional;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.http.MediaType;
import org.springframework.stereotype.Component;
import org.springframework.web.reactive.function.client.WebClient;

import com.fasterxml.jackson.databind.JsonNode;

import lombok.extern.slf4j.Slf4j;
import reactor.core.publisher.Mono;

@Slf4j
@Component
public class PublicAddr {

    @Value("${api.publicAddr.key}")
    private String confmKey;

    private final WebClient webClient;

    public PublicAddr(WebClient.Builder webClientBuilder) {
        this.webClient = webClientBuilder.baseUrl("https://business.juso.go.kr").build();
    }

    public Mono<String> getAdmCd(String keyword) {
        String url = makeUrl(keyword);

        return webClient.get()
                .uri(url)
                .accept(MediaType.APPLICATION_JSON)
                .retrieve()
                .bodyToMono(JsonNode.class)
                .map(param -> extracted(param))
                .onErrorResume(e -> {
                    log.error("행정구역코드 조회 실패 url: {}", webClient.toString() + url, e);
                    return Mono.empty();
                });
    }

    private String extracted(JsonNode rootNode) {
        return Optional.ofNullable(rootNode)
                        .filter(nodes -> nodes != null && nodes.has("results"))
                        .map(node -> node.get("results"))
                        .filter(res -> res != null && res.has("juso"))
                        .map(res -> res.get("juso"))
                        .filter(jusoNode -> jusoNode != null && jusoNode.size() > 0)
                        .map(jusoNode -> jusoNode.get(0))
                        .filter(itemNode -> itemNode != null && itemNode.has("admCd"))
                        .map(itemNode -> itemNode.get("admCd").asText())
                        .filter(admCd -> !admCd.isEmpty())
                        .orElse("");
    }

    private String makeUrl(String keyword) {
        String currentPage = "1";
        String countPerPage = "10";
        String resultType = "json";

        StringBuffer urlStrBuffer = new StringBuffer();
        
        urlStrBuffer.append("/addrlink/addrLinkApi.do?currentPage=")
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
