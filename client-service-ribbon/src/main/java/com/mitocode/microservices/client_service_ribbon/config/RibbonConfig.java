package com.mitocode.microservices.client_service_ribbon.config;

import com.netflix.loadbalancer.AvailabilityFilteringRule;
import com.netflix.loadbalancer.IPing;
import com.netflix.loadbalancer.IRule;
import com.netflix.loadbalancer.NoOpPing;
import org.springframework.context.annotation.Bean;

public class RibbonConfig {

    @Bean
    public IPing ribbonPing() {
        return new NoOpPing();
    }

    @Bean
    public IRule ribbonRule() {
        return new AvailabilityFilteringRule();
    }
}