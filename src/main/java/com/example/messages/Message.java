package com.example.messages;

import lombok.AllArgsConstructor;
import lombok.Data;
import org.springframework.data.annotation.CreatedBy;
import org.springframework.data.annotation.CreatedDate;
import org.springframework.data.annotation.LastModifiedBy;
import org.springframework.data.annotation.LastModifiedDate;
import org.springframework.data.jpa.domain.support.AuditingEntityListener;

import javax.persistence.*;
import java.util.Date;

@Entity
@Data
@AllArgsConstructor
@EntityListeners(AuditingEntityListener.class)
public class Message {

		@Id
		@GeneratedValue
		private Long id;

		private String text;

		@OneToOne
		private User to;

		@LastModifiedDate
		private Date lastModifiedDate;

		@CreatedDate
		private Date createdDate;

		@LastModifiedBy
		private String lastModifiedBy;

		@CreatedBy
		private String  createdBy;

		Message() {
		}

		public Message(String text, User to) {
				this.text = text;
				this.to = to;
		}
}
