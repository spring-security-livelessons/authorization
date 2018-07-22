package com.example.messages;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.security.access.annotation.Secured;
import org.springframework.security.access.prepost.PostAuthorize;
import org.springframework.security.access.prepost.PreAuthorize;

import javax.annotation.security.RolesAllowed;

/**
	* @author <a href="mailto:josh@joshlong.com">Josh Long</a>
	*/
public interface MessageRepository extends JpaRepository<Message, Long> {

		String QUERY = "select m from Message m where m.id = ?1";

		// TODO: make sure to configure a bean of type SecurityEvaluationContextExtension!
		@Query(QUERY)
		@PostAuthorize("@authz.check( returnObject , principal?.user  )")
		Message findByIdBeanCheck(Long id);

		@RolesAllowed("ROLE_ADMIN")
		@Query(QUERY)
		Message findByIdRolesAllowed(Long id);

		@Secured("ROLE_ADMIN")
		@Query(QUERY)
		Message findByIdSecured(Long id);

		@PreAuthorize("hasRole('ADMIN')")
		@Query(QUERY)
		Message findByIdPreAuthorize(Long id);

		// todo: demo it failing b/c we have too many records and tried to iterate over it
		// todo: in the @PostAuthorize section. instead, we can use the Spring Data integration
		// todo: to filter over the records.
		// we do this INSTEAD of the @PostAuthorize or @PostFilter
		// todo: show @PostFilter and reveal that it'd fail
		@Query("select m from Message m where m.to.id = ?#{ principal?.user?.id }")
		Page<Message> findMessagesFor(Pageable pageable);

}
