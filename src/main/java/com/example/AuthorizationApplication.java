package com.example;

import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.context.annotation.Configuration;
import org.springframework.http.HttpMethod;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.builders.WebSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.config.annotation.web.configuration.WebSecurityConfigurerAdapter;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RestController;

import javax.servlet.http.HttpServletRequest;
import java.security.Principal;
import java.util.Collection;
import java.util.HashSet;
import java.util.Map;
import java.util.Set;
import java.util.concurrent.ConcurrentHashMap;

/**
	* Lesson 6: Web Based Authorization (rob | josh)
	* 6.1 What is Authorization?
	* 6.2 Roles and Authorities
	* 6.3 SpEL primer
	* 6.4 Spring Security Expressions
	* 6.5 Encapsulating Logic into Beans
	* 6.6 Path Parameterspath parameters
	* 6.7 mvcMatchers
	* 6.8 Actuator (Endpoint.*)
	*/
@SpringBootApplication
public class AuthorizationApplication {

		public static void main(String args[]) {
				SpringApplication.run(AuthorizationApplication.class, args);
		}

}

@RestController
class UserRestController {

		private final UserDetailsService userDetailsService;

		final static String NAME = "name";

		UserRestController(UserDetailsService userDetailsService) {
				this.userDetailsService = userDetailsService;
		}

		@GetMapping("/users/{" + NAME + "}")
		UserDetails userByName(@PathVariable(NAME) String name) {
				return this.userDetailsService.loadUserByUsername(name);
		}
}

@Configuration
@EnableWebSecurity
class SecurityConfig extends WebSecurityConfigurerAdapter {

		@Override
		public void configure(WebSecurity web) throws Exception {
				web.ignoring().mvcMatchers("/foo"); // todo DON'T DO THIS!
		}

		@Override
		protected void configure(HttpSecurity http) throws Exception {
				http
					.httpBasic();

				http
					.authorizeRequests()
					.mvcMatchers("/users/{" + UserRestController.NAME + "}").access("#name == principal?.username ")
					.mvcMatchers(HttpMethod.GET, "/a").access("hasRole('ADMIN')")
					.mvcMatchers(HttpMethod.GET, "/b").access("@authz.check( request, principal )"); // Map<RequestMatcher , ConfigAttribute>
		}
}

@Service("authz")
class AuthService {

		private final Log log = LogFactory.getLog(getClass());

		public boolean check(HttpServletRequest request, Principal principal) {
				this.log.info("principal.class=" + principal.getClass().getName() + " principal.name=" + principal.getName());
				return true;
		}
}

@Service
class CustomUserDetailsService implements UserDetailsService {

		private final Map<String, UserDetails> users = new ConcurrentHashMap<>();

		CustomUserDetailsService() {
				users.put("jlong", new CustomUser("jlong", "USER"));
				users.put("rwinch", new CustomUser("rwinch", "USER", "ADMIN"));
		}

		@Override
		public UserDetails loadUserByUsername(String username) throws UsernameNotFoundException {
				return this.users.get(username);
		}
}

// this could be in the DB!
class CustomUser implements UserDetails {

		private final String username, password;
		private final boolean active;
		private final Set<GrantedAuthority> authoritySet = new HashSet<>();

		CustomUser(String u, String... authorities) {
				this(u, "password", true, authorities);
		}

		CustomUser(String u, String pw, boolean active, String... authorities) {
				this.username = u;
				this.password = pw;
				this.active = active;
				for (String a : authorities) {
						this.authoritySet.add(new SimpleGrantedAuthority(a));
				}
		}

		@Override
		public Collection<? extends GrantedAuthority> getAuthorities() {
				return this.authoritySet;
		}

		@Override
		public String getPassword() {
				return this.password;
		}

		@Override
		public String getUsername() {
				return this.username;
		}

		@Override
		public boolean isAccountNonExpired() {
				return this.active;
		}

		@Override
		public boolean isAccountNonLocked() {
				return this.active;
		}

		@Override
		public boolean isCredentialsNonExpired() {
				return this.active;
		}

		@Override
		public boolean isEnabled() {
				return this.active;
		}
}