package com.pm.projectmanager.common.response;

import org.springframework.http.HttpStatus;

import lombok.Getter;
import lombok.RequiredArgsConstructor;

@Getter
@RequiredArgsConstructor
public enum ResponseExceptionEnum {

	EMAIL_ALREADY_EXISTS(HttpStatus.BAD_REQUEST, "중복된 이메일입니다."),
	NICKNAME_ALREADY_EXISTS(HttpStatus.BAD_REQUEST, "중복된 닉네임입니다."),
	PROJECT_NOT_FOUND(HttpStatus.NOT_FOUND, "프로젝트를 찾을 수 없습니다."),
	AUTHORITY_NULL_EXCEPTION(HttpStatus.FORBIDDEN, "해당 프로젝트에 권한이 없습니다."),
	NO_INVITE_EXCEPTION(HttpStatus.NOT_FOUND, "해당 프로젝트에서 받은 초대가 없습니다."),
	SECTION_NOT_FOUND(HttpStatus.NOT_FOUND, "섹션을 찾을 수 없습니다."),
	SECTION_ALREADY_EXISTS(HttpStatus.BAD_REQUEST, "중복된 섹션명입니다.");

	private final HttpStatus httpStatus;
	private final String message;
}