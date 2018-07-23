package com.example;

import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.security.access.AccessDecisionManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.test.context.junit4.SpringRunner;

@SpringBootTest
@RunWith(SpringRunner.class)
public class AccessDecisionManagerTest {

		@Autowired
		private AccessDecisionManager accessDecisionManager;

		@Test
		public void test() throws Exception {

				UsernamePasswordAuthenticationToken token =
					new UsernamePasswordAuthenticationToken("jlong", "password");

		}
}
