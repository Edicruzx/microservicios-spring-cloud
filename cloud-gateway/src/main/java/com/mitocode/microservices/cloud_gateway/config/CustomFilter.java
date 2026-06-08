package com.mitocode.microservices.cloud_gateway.config;

import lombok.Data;
import lombok.extern.slf4j.Slf4j;
import org.springframework.cloud.gateway.filter.GatewayFilter;
import org.springframework.cloud.gateway.filter.factory.AbstractGatewayFilterFactory;
import org.springframework.http.ResponseCookie;
import org.springframework.stereotype.Component;
import reactor.core.publisher.Mono;

@Slf4j
//@Component
public class CustomFilter extends AbstractGatewayFilterFactory<CustomFilter.CustomConfiguration> {

    public CustomFilter() {
        super(CustomConfiguration.class);
    }

    @Override
    public GatewayFilter apply(CustomConfiguration config) {
        return ((exchange, chain) -> {

            log.info("[CustomFilter] - [apply]: Pre filter");

            exchange.getRequest()
                    .mutate()
                    .header("callerName", config.headerValue);

            return chain.filter(exchange).then(Mono.fromRunnable(() -> {

                exchange.getResponse()
                        .getCookies()
                        .add(config.headerKey,
                                ResponseCookie.from(config.headerKey, config.headerValue).build());

                exchange.getResponse()
                        .getHeaders()
                        .add("RESPONSE", config.headerValue);

                log.info("HeaderKey : " + config.headerKey);
                log.info("HeaderValue : " + config.headerValue);
                log.info("[CustomFilter] - [apply]: Post filter");
            }));
        });
    }

    @Data
    public static class CustomConfiguration {
        private String headerKey;
        private String headerValue;
    }
}