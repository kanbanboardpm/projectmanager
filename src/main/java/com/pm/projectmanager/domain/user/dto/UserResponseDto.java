package com.pm.projectmanager.domain.user.dto;

import com.pm.projectmanager.domain.user.User;

import lombok.Getter;

@Getter
public class UserResponseDto {

	private String email;
	private String nickname;
	private String image_url;

	public UserResponseDto(User user) {
		this.email = user.getEmail();
		this.nickname = user.getNickname();
		this.image_url = user.getPhotoUrl();
	}
}
