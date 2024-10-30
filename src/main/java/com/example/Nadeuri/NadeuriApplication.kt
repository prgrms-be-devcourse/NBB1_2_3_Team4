package com.example.Nadeuri;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.data.jpa.repository.config.EnableJpaAuditing;

@EnableJpaAuditing
@SpringBootApplication
public class NadeuriApplication {

	public static void main(String[] args) {
		SpringApplication.run(NadeuriApplication.class, args);
	}

}
