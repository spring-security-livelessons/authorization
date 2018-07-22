package com.example.messages;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.Profile;
import org.springframework.data.domain.AuditorAware;
import org.springframework.data.jpa.repository.config.EnableJpaAuditing;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.data.repository.query.SecurityEvaluationContextExtension;

import java.util.Optional;

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

@Configuration
@Profile(AuditingConfig.AUDITOR)
@EnableJpaAuditing(auditorAwareRef = AuditingConfig.AUDITOR)
class AuditingConfig {

		public static final String AUDITOR = "auditing";

		@Bean(AUDITOR)
		AuditorAware<String> auditor() {
				return () -> {
						Authentication authentication = SecurityContextHolder.getContext().getAuthentication();
						UserRepositoryUserDetailsService.CustomUserDetails customUserDetails =
							UserRepositoryUserDetailsService.CustomUserDetails.class.cast(authentication.getPrincipal());
						return Optional.ofNullable(customUserDetails.getUser().getEmail());
				};
		}
}