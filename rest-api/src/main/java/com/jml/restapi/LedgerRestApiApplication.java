package com.jml.restapi;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.boot.autoconfigure.domain.EntityScan;
import org.springframework.data.jpa.repository.config.EnableJpaRepositories;

@SpringBootApplication(scanBasePackages = "com.jml")
@EnableJpaRepositories
@EntityScan(basePackages = "com.jml.repository.model")
public class LedgerRestApiApplication {

	public static void main(String[] args) {
		SpringApplication.run(LedgerRestApiApplication.class, args);
	}

}
