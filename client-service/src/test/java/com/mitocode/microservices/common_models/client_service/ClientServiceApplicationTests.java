package com.mitocode.microservices.common_models.client_service;

import org.junit.jupiter.api.Test;
import org.springframework.boot.test.context.SpringBootTest;

@SpringBootTest(properties = {
		"spring.cloud.config.enabled=false",
		"eureka.client.enabled=false"
})
class ClientServiceApplicationTests {

	@Test
	void contextLoads() {
	}

}
