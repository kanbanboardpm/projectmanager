package com.pm.projectmanager.common.response;

import org.springframework.http.HttpStatus;

import lombok.Getter;
import lombok.RequiredArgsConstructor;

@Getter
@RequiredArgsConstructor
public enum ResponseCodeEnum {
	// user
    USER_SIGNUP_SUCCESS(HttpStatus.OK, "회원가입 성공"),
    // category
    CATEGORY_CREATE_SUCCESS(HttpStatus.OK, "카테고리 생성 성공");

	private final HttpStatus httpStatus;
	private final String message;
}
