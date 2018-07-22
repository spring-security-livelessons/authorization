package com.example.messages;

import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;
import org.junit.Assert;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.test.context.ActiveProfiles;
import org.springframework.test.context.junit4.SpringRunner;
import org.springframework.transaction.annotation.Transactional;

/**
	* @author <a href="mailto:josh@joshlong.com">Josh Long</a>
	*/
@RunWith(SpringRunner.class)
@SpringBootTest
@Transactional
@ActiveProfiles(AuditingConfig.AUDITOR)
public class AuditingTest {

		@Autowired
		private MessageRepository messageRepository;

		@Autowired
		private UserRepository userRepository;

		@Autowired
		private AuthorityRepository authorityRepository;

		@Autowired
		private UserDetailsService userDetailsService;

		private void installAuthentication(String username) {
				UserDetails principal = this.userDetailsService.loadUserByUsername(username);
				Authentication authentication = new UsernamePasswordAuthenticationToken(
					principal, principal.getPassword(), principal.getAuthorities());
				SecurityContextHolder.getContext().setAuthentication(authentication);
		}

		@Test
		public void confirmAuditing() throws Exception {
				Authority admin = this.authorityRepository.save(new Authority("ADMIN"));
				Authority user = this.authorityRepository.save(new Authority("USER"));
				User josh = this.userRepository.save(new User("josh", "password", user));
				installAuthentication(josh.getEmail());
				User rob = this.userRepository.save(new User("rob", "password", user, admin));
				Message msg = this.messageRepository.save(new Message("hello world!", rob));
				log.info("message: " + msg.toString());
				User cb = userRepository.findByEmail(msg.getCreatedBy());
				Assert.assertEquals(cb.getId(), josh.getId());
		}

		private final Log log = LogFactory.getLog(getClass());
}
