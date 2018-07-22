package com.example.messages;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.EqualsAndHashCode;

import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.Id;
import javax.persistence.ManyToMany;
import java.util.*;

@Data

@AllArgsConstructor
@EqualsAndHashCode(exclude = "authorities")
@Entity
public class User {

		@Id
		@GeneratedValue
		private Long id;
		private String email;
		private String password;

		@ManyToMany(mappedBy = "users")
		private List<Authority> authorities = new ArrayList<>();

		User() {
				this(null, null, new HashSet<>());
		}

		User(String u, String pw, Set<Authority> authorities) {
				this.email = u;
				this.password = pw;
				authorities.forEach(this.authorities::add);
		}

		User(String u, String pw, Authority... auths) {
				this(u, pw, new HashSet<>(Arrays.asList(auths)));
		}

		User(String e, String pw) {
				this(e, pw, new HashSet<>());
		}
}
