package com.example.messages;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.EqualsAndHashCode;
import lombok.ToString;

import javax.persistence.*;
import java.util.ArrayList;
import java.util.HashSet;
import java.util.List;
import java.util.Set;

@Data
@AllArgsConstructor
@ToString(exclude = "users")
@Entity
@EqualsAndHashCode(exclude = "users")
public class Authority {

		@Id
		@GeneratedValue
		private Long id;

		private String authority;

		Authority() {
		}

		Authority(String authority) {
				this(authority, new HashSet<>());
		}

		Authority(String authority, Set<User> users) {
				this.authority = authority;
				users.forEach(this.users::add);
		}


		@ManyToMany(cascade = {
			CascadeType.PERSIST,
			CascadeType.MERGE
		})
		@JoinTable(name = "authority_user",
			joinColumns = @JoinColumn(name = "authority_id"),
			inverseJoinColumns = @JoinColumn(name = "user_id")
		)
		private List<User> users = new ArrayList<>();


}
