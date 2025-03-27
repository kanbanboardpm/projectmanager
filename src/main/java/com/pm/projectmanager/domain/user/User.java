package com.pm.projectmanager.domain.user;

import java.time.LocalDateTime;

import jakarta.persistence.*;

import com.pm.projectmanager.common.TimeStamp;

import jakarta.validation.constraints.Email;
import lombok.AccessLevel;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;

@Entity
@Getter
@NoArgsConstructor(access = AccessLevel.PROTECTED)
@Table(name = "users", indexes = {
	@Index(name = "idx_email", columnList = "email")
})
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

	private LocalDateTime isDeleted;

	private Long kakaoId;

	@Builder
	public User(String email, String password, String nickname, String photoUrl, Long kakaoId) {
		this.email = email;
		this.password = password;
		this.nickname = nickname;
		this.photoUrl = photoUrl;
		this.kakaoId = kakaoId;
	}

	public void update(String nickname, String photoUrl) {
		this.nickname = nickname;
		this.photoUrl = photoUrl;
	}

	public void updatePassword(String password) {
		this.password = password;
	}

	public void withdraw(LocalDateTime isDeleted) {
		this.isDeleted = isDeleted;
	}

	public void updateKakaoId(Long kakaoId) {
		this.kakaoId = kakaoId;
	}
}
