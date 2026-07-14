package com.mitocode.microservices.userservice;

import com.mitocode.microservices.commonmodels.model.entity.UserEntity;
import com.mitocode.microservices.userservice.repository.UserRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.CommandLineRunner;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.beans.factory.annotation.Value;

@SpringBootApplication
public class UserServiceApplication implements CommandLineRunner {

	@Autowired
	private UserRepository repository;

	@Value("${app.seed.enabled:false}")
	private boolean seedEnabled;

	public static void main(String[] args) {
		SpringApplication.run(UserServiceApplication.class, args);
	}

	@Override
	public void run(String... args) throws Exception {
		if (!seedEnabled || repository.count() > 0) {
			return;
		}

		repository.save(UserEntity.builder()
				.name("Jonathan")
				.lastname("Choy")
				.email("springcloud@mitocodenetwork.com")
				.username("jchoy")
				.password("mitocode")
				.roles(new String[]{"ROLE_USER"})
				.build());

		repository.save(UserEntity.builder()
				.name("Mitocode")
				.lastname("Network")
				.email("admin@mitocodenetwork.com")
				.username("mitocode")
				.password("mitocode")
				.roles(new String[]{"ROLE_ADMIN"})
				.build());

	}
}
