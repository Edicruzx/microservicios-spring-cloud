package com.mitocode.microservices.authentication_client_oauth2;

import org.junit.jupiter.api.Test;
import org.springframework.boot.test.context.SpringBootTest;

@SpringBootTest(properties = {
		"spring.cloud.config.enabled=false",
		"eureka.client.enabled=false",
		"spring.security.oauth2.client.registration.google.client-id=test-client",
		"spring.security.oauth2.client.registration.google.client-secret=test-secret"
})
class AuthenticationClientOauth2ApplicationTests {

	@Test
	void contextLoads() {
	}

}
