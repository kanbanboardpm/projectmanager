package com.pm.projectmanager.domain.user.dto;

import lombok.Getter;

@Getter
public class KakaoLoginResponseDto {
	private String token;

	public KakaoLoginResponseDto(String token) {
		this.token = token;
	}
}
