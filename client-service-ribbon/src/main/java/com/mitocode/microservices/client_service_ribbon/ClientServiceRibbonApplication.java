package com.mitocode.microservices.client_service_ribbon;

import com.mitocode.microservices.client_service_ribbon.config.RibbonConfig;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.cloud.netflix.ribbon.RibbonClient;
import org.springframework.cloud.netflix.ribbon.RibbonClients;

@RibbonClients({
	@RibbonClient(name = "user-service", configuration = RibbonConfig.class),
	@RibbonClient(name = "product-service", configuration = RibbonConfig.class)
})
@SpringBootApplication
public class ClientServiceRibbonApplication {

	public static void main(String[] args) {
		SpringApplication.run(ClientServiceRibbonApplication.class, args);
	}
}