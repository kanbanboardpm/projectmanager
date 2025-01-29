package com.pm.projectmanager.common.response;

import org.springframework.http.HttpStatus;

import lombok.Getter;
import lombok.RequiredArgsConstructor;

@Getter
@RequiredArgsConstructor
public enum ResponseCodeEnum {
	USER_SIGNUP_SUCCESS(HttpStatus.CREATED, "회원가입 성공"),
	USER_LOGOUT_SUCCESS(HttpStatus.OK, "로그아웃 성공"),
	PROJECT_CREATE_SUCCESS(HttpStatus.CREATED, "프로젝트 생성 성공"),
	PROJECT_GET_SUCCESS(HttpStatus.OK, "프로젝트 조회 성공"),
	PROJECT_UPDATE_SUCCESS(HttpStatus.OK, "프로젝트 수정 성공"),
	PROJECT_DELETE_SUCCESS(HttpStatus.OK, "프로젝트 삭제 성공"),
	PROJECT_INVITE_SUCCESS(HttpStatus.OK, "프로젝트 초대 성공"),
	PROJECT_ACCEPT_SUCCESS(HttpStatus.OK, "프로젝트 수락 성공"),
    // category
    CATEGORY_CREATE_SUCCESS(HttpStatus.OK, "카테고리 생성 성공");

	private final HttpStatus httpStatus;
	private final String message;
}
