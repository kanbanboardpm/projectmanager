package com.pm.projectmanager.common.response;

import org.springframework.http.HttpStatus;

import lombok.Getter;
import lombok.RequiredArgsConstructor;

@Getter
@RequiredArgsConstructor
public enum ResponseCodeEnum {
	TEST_SUCCESS(HttpStatus.OK, "테스트 성공");

	private final HttpStatus httpStatus;
	private final String message;
}
