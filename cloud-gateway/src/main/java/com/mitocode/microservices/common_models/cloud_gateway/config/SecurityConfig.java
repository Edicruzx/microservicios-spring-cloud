package com.mitocode.microservices.common_models.cloud_gateway.config;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.config.web.server.ServerHttpSecurity;
import org.springframework.security.web.server.SecurityWebFilterChain;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.security.oauth2.jwt.NimbusReactiveJwtDecoder;
import org.springframework.security.oauth2.jwt.ReactiveJwtDecoder;
import org.springframework.security.oauth2.jwt.ReactiveJwtDecoders;
import org.springframework.boot.autoconfigure.condition.ConditionalOnProperty;
import javax.crypto.spec.SecretKeySpec;
import java.nio.charset.StandardCharsets;

@Configuration
public class SecurityConfig {

    @Bean
    public SecurityWebFilterChain securityWebFilterChain(ServerHttpSecurity http) {
        return http
                .csrf(ServerHttpSecurity.CsrfSpec::disable)
                .authorizeExchange(exchange -> exchange
                        .pathMatchers("/actuator/**").permitAll()
                        .pathMatchers("/api-docs/**", "/swagger-ui/**").permitAll()
                        .pathMatchers("/api/user-service/**").permitAll()
                        .anyExchange().authenticated()
                )
                .oauth2ResourceServer(oauth2 -> oauth2.jwt(jwt -> {}))
                .build();
    }

    @Bean
    @ConditionalOnProperty(name = "security.provider", havingValue = "legacy", matchIfMissing = true)
    public ReactiveJwtDecoder jwtDecoder(
            @Value("${mitocode.security.key:mitocode-microservices-jwt-key-2026}") String signingKey
    ) {
        SecretKeySpec secretKey = new SecretKeySpec(signingKey.getBytes(StandardCharsets.UTF_8), "HmacSHA256");
        return NimbusReactiveJwtDecoder.withSecretKey(secretKey).build();
    }

    @Bean
    @ConditionalOnProperty(name = "security.provider", havingValue = "keycloak")
    public ReactiveJwtDecoder keycloakJwtDecoder(
            @Value("${spring.security.oauth2.resourceserver.jwt.issuer-uri}") String issuerUri
    ) {
        return ReactiveJwtDecoders.fromIssuerLocation(issuerUri);
    }
}
