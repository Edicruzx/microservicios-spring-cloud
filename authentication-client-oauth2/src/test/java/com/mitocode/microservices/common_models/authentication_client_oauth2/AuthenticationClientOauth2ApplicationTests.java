package com.mitocode.microservices.common_models.authentication_client_oauth2;

import org.junit.jupiter.api.Test;
import org.springframework.boot.test.context.SpringBootTest;

@SpringBootTest(properties = {
        "spring.cloud.config.enabled=false",
        "eureka.client.enabled=false",
        "spring.security.oauth2.client.registration.github.client-id=test-client-id",
        "spring.security.oauth2.client.registration.github.client-secret=test-client-secret"
})
class AuthenticationClientOauth2ApplicationTests {

    @Test
    void contextLoads() {
    }

}
