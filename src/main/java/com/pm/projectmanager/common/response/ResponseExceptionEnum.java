package com.pm.projectmanager.common.response;

import org.springframework.http.HttpStatus;

import lombok.Getter;
import lombok.RequiredArgsConstructor;

@Getter
@RequiredArgsConstructor
public enum ResponseExceptionEnum {

    // user
	USERNAME_ALREADY_EXISTS(HttpStatus.BAD_REQUEST, "중복된 아이디입니다."),
	NICKNAME_ALREADY_EXISTS(HttpStatus.BAD_REQUEST, "중복된 닉네임입니다."),

    // category
    CATEGORY_NAME_ALREADY_EXISTS_IN_PROJECT(HttpStatus.BAD_REQUEST, "프로젝트 내에서 카테고리명이 이미 존재합니다.");

	private final HttpStatus httpStatus;
	private final String message;
}