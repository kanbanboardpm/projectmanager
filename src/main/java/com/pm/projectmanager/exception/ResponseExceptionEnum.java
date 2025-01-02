package com.pm.projectmanager.exception;

import org.springframework.http.HttpStatus;

import lombok.Getter;
import lombok.RequiredArgsConstructor;

@Getter
@RequiredArgsConstructor
public enum ResponseExceptionEnum {

	EXCEPTION_TEST(HttpStatus.BAD_REQUEST, "예외처리 예시입니다.");
	private final HttpStatus httpStatus;
	private final String message;
}