package com.example.messages;

import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;
import org.springframework.stereotype.Component;

/**
	* @author <a href="mailto:josh@joshlong.com">Josh Long</a>
	*/
@Component("authz")
public class AuthService {

		private final Log log = LogFactory.getLog(getClass());

		public boolean check(Message msg, User u) {
				this.log.info("checking " + msg.getText() + " for access to user " + u.getId() + " by authenticated user " + u.getId() + ".");
				return u.getId().equals(msg.getTo().getId());
		}
}
