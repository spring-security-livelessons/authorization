package com.example.messages;

import org.springframework.data.jpa.repository.JpaRepository;

/**
	* @author <a href="mailto:josh@joshlong.com">Josh Long</a>
	*/
public interface AuthorityRepository extends JpaRepository<Authority, Long> {

		Authority findByAuthority (String a ) ;
}
