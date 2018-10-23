package com.plexus.ejerciciousuario;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;

/**
 * Clase main de la aplicación
 * 
 * @author dlrjad
 * @version 1.0
 * @since 22/10/2018 
 */
@SpringBootApplication
public class DemoApplication {

	public static void main(String[] args) {
		final Logger logger = LoggerFactory.getLogger(DemoApplication.class);
		SpringApplication.run(DemoApplication.class, args);
		logger.debug("--Application Started--");
		logger.trace("--TRACE--");
	}
}
