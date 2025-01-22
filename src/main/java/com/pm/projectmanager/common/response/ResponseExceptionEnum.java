package com.pm.projectmanager.common.response;

import org.springframework.http.HttpStatus;

import lombok.Getter;
import lombok.RequiredArgsConstructor;

@Getter
@RequiredArgsConstructor
public enum ResponseExceptionEnum {

	USERNAME_ALREADY_EXISTS(HttpStatus.BAD_REQUEST, "중복된 아이디입니다."),
	NICKNAME_ALREADY_EXISTS(HttpStatus.BAD_REQUEST, "중복된 닉네임입니다.");
	private final HttpStatus httpStatus;
	private final String message;
}