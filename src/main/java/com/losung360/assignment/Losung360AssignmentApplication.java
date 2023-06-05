package com.losung360.assignment;

import org.modelmapper.ModelMapper;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.boot.autoconfigure.security.servlet.SecurityAutoConfiguration;
import org.springframework.context.annotation.Bean;

@SpringBootApplication(exclude = SecurityAutoConfiguration.class)
public class Losung360AssignmentApplication {
	public static void main(String[] args) {
		SpringApplication.run(Losung360AssignmentApplication.class, args);
	}

	@Bean
	public ModelMapper modelMapper() {
		return new ModelMapper();
	}

}
