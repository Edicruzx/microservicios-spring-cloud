package com.mitocode.microservices.authentication_server_oauth2;

import lombok.RequiredArgsConstructor;
import org.springframework.boot.CommandLineRunner;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;

@SpringBootApplication
@RequiredArgsConstructor
public class AuthenticationServerOauth2Application implements CommandLineRunner {

	private final BCryptPasswordEncoder encoder;

	public static void main(String[] args) {
		SpringApplication.run(AuthenticationServerOauth2Application.class, args);
	}

	@Override
	public void run(String... args) throws Exception {
		System.out.println("========================================");
		System.out.println(encoder.encode("mitocode"));
	}
}