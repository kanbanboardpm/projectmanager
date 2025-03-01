package com.pm.projectmanager.common.response;

import org.springframework.http.HttpStatus;

import lombok.Getter;
import lombok.RequiredArgsConstructor;

@Getter
@RequiredArgsConstructor
public enum ResponseExceptionEnum {
	// user
	EMAIL_ALREADY_EXISTS(HttpStatus.BAD_REQUEST, "중복된 이메일입니다."),
	NICKNAME_ALREADY_EXISTS(HttpStatus.BAD_REQUEST, "중복된 닉네임입니다."),
	PASSWORD_INCORRECT(HttpStatus.BAD_REQUEST, "패스워드가 일치하지 않습니다."),
	PASSWORD_EQUAL(HttpStatus.BAD_REQUEST, "이전 비밀번호와 동일합니다."),
    USER_NOT_FOUND(HttpStatus.BAD_REQUEST, "존재하지 않는 유저 입니다."),
	// project
	PROJECT_NOT_FOUND(HttpStatus.NOT_FOUND, "프로젝트를 찾을 수 없습니다."),
	AUTHORITY_NULL_EXCEPTION(HttpStatus.FORBIDDEN, "해당 프로젝트에 권한이 없습니다."),
	NO_INVITE_EXCEPTION(HttpStatus.NOT_FOUND, "해당 프로젝트에서 받은 초대가 없습니다."),
	AUTHORITY_ALREADY_EXISTS(HttpStatus.BAD_REQUEST, "이미 초대된 유저입니다."),
    // category
    CATEGORY_NAME_ALREADY_EXISTS_IN_PROJECT(HttpStatus.BAD_REQUEST, "프로젝트 내에서 카테고리명이 이미 존재합니다."),
    CATEGORY_NOT_FOUND(HttpStatus.NOT_FOUND, "카테고리를 찾을 수 없습니다."),
    CATEGORY_DELETE_RELATED_CARDS_EXIST(HttpStatus.BAD_REQUEST, "관련 된 카드를 제거해야 카테고리를 지울 수 있습니다."),
	// section
	SECTION_NOT_FOUND(HttpStatus.NOT_FOUND, "섹션을 찾을 수 없습니다."),
	SECTION_ALREADY_EXISTS(HttpStatus.BAD_REQUEST, "중복된 섹션명입니다."),
    // card
    CARD_NOT_FOUND(HttpStatus.NOT_FOUND, "카드를 찾을 수 없습니다."),
    CARD_ALREADY_COMPLETED(HttpStatus.BAD_REQUEST, "이미 완료 되어있는 카드 입니다."),
    CARD_ALREADY_IN_PROGRESS(HttpStatus.BAD_REQUEST, "이미 진행 되고있는 카드 입니다."),
    // comment
    COMMENT_NOT_FOUND(HttpStatus.NOT_FOUND, "댓글을 찾을 수 없습니다."),
    // notification
    NOTIFICATION_NOT_FOUND(HttpStatus.NOT_FOUND, "댓글 관련 알림이 없습니다."),
	// authority
	ADMIN_ROLE_REQUIRED(HttpStatus.FORBIDDEN, "관리자 권한이 필요합니다.");

	private final HttpStatus httpStatus;
	private final String message;
}