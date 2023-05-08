package com.rasp.securityauthentication;

import com.pi4j.Pi4J;
import com.pi4j.context.Context;
import lombok.extern.slf4j.Slf4j;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.context.annotation.Bean;

@Slf4j
@SpringBootApplication
public class DistanceSensor {

	public static void main(String[] args) {
		SpringApplication.run(DistanceSensor.class, args);
	}

	/**
	 * Spring to handle initialization of PI4J context.
	 * @return Context.
	 */
	@Bean
	public Context getContext() {
		return Pi4J.newAutoContext();
	}
}
