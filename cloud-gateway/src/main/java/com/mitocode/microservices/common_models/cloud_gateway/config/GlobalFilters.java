package com.mitocode.microservices.common_models.cloud_gateway.config;

import lombok.extern.slf4j.Slf4j;
import org.springframework.cloud.gateway.filter.GatewayFilterChain;
import org.springframework.cloud.gateway.filter.GlobalFilter;
import org.springframework.http.ResponseCookie;
import org.springframework.stereotype.Component;
import org.springframework.web.server.ServerWebExchange;
import reactor.core.publisher.Mono;

@Slf4j
//@Component
public class GlobalFilters implements GlobalFilter {

    @Override
    public Mono<Void> filter(ServerWebExchange exchange,
                             GatewayFilterChain chain) {

        log.info("[GlobalFilters] - [filter]: Pre filter");

        // Prefilter
        exchange.getRequest()
                .mutate()
                .header("callerName", "Cloud-Gateway");

        return chain.filter(exchange)
                .then(Mono.fromRunnable(() -> {

                    log.info("[GlobalFilters] - [filter]: Post filter");

                    exchange.getResponse()
                            .getCookies()
                            .add("RESPONSE",
                                    ResponseCookie
                                            .from("CALLER_NAME", "Cloud-Gateway")
                                            .build());

                    exchange.getResponse()
                            .getHeaders()
                            .add("RESPONSE", "Cloud-Gateway");
                }));
    }
}