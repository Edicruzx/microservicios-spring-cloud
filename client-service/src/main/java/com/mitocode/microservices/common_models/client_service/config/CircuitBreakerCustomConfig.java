package com.mitocode.microservices.common_models.client_service.config;


import io.github.resilience4j.circuitbreaker.CircuitBreakerConfig;
import io.github.resilience4j.timelimiter.TimeLimiterConfig;
import org.springframework.cloud.circuitbreaker.resilience4j.Resilience4JCircuitBreakerFactory;
import org.springframework.cloud.circuitbreaker.resilience4j.Resilience4JConfigBuilder;
import org.springframework.cloud.client.circuitbreaker.Customizer;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

import java.time.Duration;


@Configuration
public class CircuitBreakerCustomConfig {

    @Bean
    public Customizer<Resilience4JCircuitBreakerFactory> overrideDefaultConfig() {
        return factory -> factory.configureDefault(id ->
                new Resilience4JConfigBuilder(id)
                        .circuitBreakerConfig(CircuitBreakerConfig.custom()
                                .slidingWindowSize(15) //default 100      //-metricas
                                .failureRateThreshold(30)  //default 50%
                                .waitDurationInOpenState(Duration.ofSeconds(20L))  //default 60 seg
                                .permittedNumberOfCallsInHalfOpenState(10)  //default 10
                                .slowCallDurationThreshold(Duration.ofMillis(1500L)) //default 1seg
                                .slowCallRateThreshold(10)
                                .build())
                        .timeLimiterConfig(TimeLimiterConfig.custom()
                                        .timeoutDuration(Duration.ofSeconds(5L))
                                .build())
                        .build());
    }
}
