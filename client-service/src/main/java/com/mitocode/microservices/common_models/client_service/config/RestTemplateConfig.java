package com.mitocode.microservices.common_models.client_service.config;


import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.web.client.RestTemplate;

@Configuration
public class RestTemplateConfig {

    @Bean("default")
    public RestTemplate configtRestTemplate() {
        return new RestTemplate();
    }

}
