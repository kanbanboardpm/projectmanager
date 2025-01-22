package com.pm.projectmanager.domain.user;

import com.pm.projectmanager.common.TimeStamp;

import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
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
	private String username;

	@Column(nullable = false)
	private String password;

	@Column(nullable = false)
	private String nickname;

	@Column(length = 4000)
	private String photoUrl;

	@Builder
	public User(String username, String password, String nickname, String photoUrl) {
		this.username = username;
		this.password = password;
		this.nickname = nickname;
		this.photoUrl = photoUrl;
	}
}
