package com.example.messages;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.context.annotation.Bean;
import org.springframework.security.data.repository.query.SecurityEvaluationContextExtension;

@SpringBootApplication
public class MessageApplication {

		/*
			* TODO: remember to install `org.springframework.security`:`spring-security-data`!
			*/
		@Bean
		SecurityEvaluationContextExtension securityEvaluationContextExtension() {
				return new SecurityEvaluationContextExtension();
		}

		public static void main(String[] args) {
				SpringApplication.run(MessageApplication.class, args);
		}
}
