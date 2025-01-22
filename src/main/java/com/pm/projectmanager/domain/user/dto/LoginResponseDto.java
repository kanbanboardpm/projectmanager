package com.pm.projectmanager.domain.user.dto;

import lombok.Getter;

@Getter
public class LoginResponseDto {

	private String token;

	public LoginResponseDto(String token) {
		this.token = token;
	}
}
