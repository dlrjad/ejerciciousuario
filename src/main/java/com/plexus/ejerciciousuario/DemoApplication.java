package com.plexus.ejerciciousuario;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;

@SpringBootApplication
public class DemoApplication {

	public static void main(String[] args) {
		final Logger logger = LoggerFactory.getLogger(DemoApplication.class);
		SpringApplication.run(DemoApplication.class, args);
		logger.debug("--Application Started--");
	}
}
