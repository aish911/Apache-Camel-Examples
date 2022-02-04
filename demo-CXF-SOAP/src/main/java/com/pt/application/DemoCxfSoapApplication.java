package com.pt.application;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.EnableAutoConfiguration;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.context.annotation.ComponentScan;
import org.springframework.web.servlet.config.annotation.EnableWebMvc;

@SpringBootApplication
@ComponentScan("com.pt.*")
@EnableAutoConfiguration
@EnableWebMvc
public class DemoCxfSoapApplication {

	public static void main(String[] args) {
		SpringApplication.run(DemoCxfSoapApplication.class, args);
	}

}
