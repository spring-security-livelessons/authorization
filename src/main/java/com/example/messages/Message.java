package com.example.messages;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import javax.persistence.*;


@Entity
@Data
@AllArgsConstructor
public class Message {

		@Id
		@GeneratedValue
		private Long id;

		private String text;

		@OneToOne
		private  User to;

		Message() {
		}

		public Message(String text, User to) {
				this.text = text;
				this.to = to;
		}
}
