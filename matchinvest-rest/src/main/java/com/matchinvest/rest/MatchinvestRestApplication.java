package com.matchinvest.rest;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;

@SpringBootApplication
public class MatchinvestRestApplication {

	public static void main(String[] args) {
		System.setProperty("org.jboss.logging.provider", "slf4j");
		SpringApplication.run(MatchinvestRestApplication.class, args);
	}

}
