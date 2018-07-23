package com.example;

import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.security.test.context.support.WithUserDetails;
import org.springframework.test.context.junit4.SpringRunner;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.request.MockMvcRequestBuilders;
import org.springframework.test.web.servlet.result.MockMvcResultMatchers;

@SpringBootTest
@AutoConfigureMockMvc
@RunWith(SpringRunner.class)
public class AuthorizationApplicationTest {

		@Autowired
		private MockMvc mockMvc;

		@Test
		@WithUserDetails("jlong")
		public void customAccess() throws Exception {
				this.mockMvc.perform(MockMvcRequestBuilders.get("/users/jlong")).andExpect(MockMvcResultMatchers.status().isOk());
				this.mockMvc.perform(MockMvcRequestBuilders.get("/users/rwinch")).andExpect(MockMvcResultMatchers.status().is4xxClientError());
		}
}