package com.pm.projectmanager.domain.user;

import com.pm.projectmanager.common.TimeStamp;

import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.validation.constraints.Email;
import lombok.AccessLevel;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;

@Entity
@Getter
@NoArgsConstructor(access = AccessLevel.PROTECTED)
public class User extends TimeStamp {

	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	private Long id;

	@Column(nullable = false, unique = true)
	@Email
	private String email;

	@Column(nullable = false)
	private String password;

	@Column(nullable = false)
	private String nickname;

	@Column(length = 4000)
	private String photoUrl;

	@Builder
	public User(String email, String password, String nickname, String photoUrl) {
		this.email = email;
		this.password = password;
		this.nickname = nickname;
		this.photoUrl = photoUrl;
	}

	public void update(String password, String nickname, String photoUrl) {
		this.password = password;
		this.nickname = nickname;
		this.photoUrl = photoUrl;
	}
}
