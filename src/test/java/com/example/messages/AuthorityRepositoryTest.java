package com.example.messages;

import org.junit.Assert;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.orm.jpa.DataJpaTest;
import org.springframework.test.context.junit4.SpringRunner;

/**
	* @author <a href="mailto:josh@joshlong.com">Josh Long</a>
	*/
@RunWith(SpringRunner.class)
@DataJpaTest
public class AuthorityRepositoryTest {

		@Autowired
		private AuthorityRepository authorityRepository;

		@Test
		public void create() throws Exception {
				authorityRepository.save(new Authority("USER"));
				authorityRepository.save(new Authority("ADMIN"));

				Assert.assertEquals(this.authorityRepository.findAll().size(), 2);
		}
}