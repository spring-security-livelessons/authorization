package com.example.messages;

import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Component;

import java.util.Collection;
import java.util.stream.Collectors;

/**
	* @author <a href="mailto:josh@joshlong.com">Josh Long</a>
	*/
@Component
public class UserRepositoryUserDetailsService implements UserDetailsService {

		private final UserRepository users;

		public UserRepositoryUserDetailsService(UserRepository users) {
				this.users = users;
		}


		private final Log log = LogFactory.getLog(getClass());

		@Override
		public UserDetails loadUserByUsername(String username) throws UsernameNotFoundException {
				User user = users.findByEmail(username);
				if (null == user) {
						throw new UsernameNotFoundException("can't find " + username + "!");
				}
				return new CustomUserDetails(user);
		}

		static final class CustomUserDetails implements UserDetails {

				private final User user;

				private final Collection<GrantedAuthority> authorities;

				CustomUserDetails(User user) {
						this.user = user;
						this.authorities = this.user.getAuthorities()
							.stream()
							.map(au -> new SimpleGrantedAuthority( "ROLE_" +au.getAuthority()))
							.collect(Collectors.toSet());
				}

				@Override
				public Collection<? extends GrantedAuthority> getAuthorities() {
						return this.authorities;
				}

				@Override
				public String getPassword() {
						return this.user.getPassword();
				}

				public User getUser() {
						return user;
				}

				@Override
				public String getUsername() {
						return user.getEmail();
				}

				@Override
				public boolean isAccountNonExpired() {
						return true;
				}

				@Override
				public boolean isAccountNonLocked() {
						return true;
				}

				@Override
				public boolean isCredentialsNonExpired() {
						return true;
				}

				@Override
				public boolean isEnabled() {
						return true;
				}
		}
}
