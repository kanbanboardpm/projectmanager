package com.pm.projectmanager.common.response;

import org.springframework.http.HttpStatus;

import lombok.Getter;
import lombok.RequiredArgsConstructor;

@Getter
@RequiredArgsConstructor
public enum ResponseCodeEnum {
	USER_SIGNUP_SUCCESS(HttpStatus.OK, "회원가입 성공"),
	USER_LOGOUT_SUCCESS(HttpStatus.OK, "로그아웃 성공");

	private final HttpStatus httpStatus;
	private final String message;
}
