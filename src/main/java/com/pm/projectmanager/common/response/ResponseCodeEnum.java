package com.pm.projectmanager.common.response;

import org.springframework.http.HttpStatus;

import lombok.Getter;
import lombok.RequiredArgsConstructor;

@Getter
@RequiredArgsConstructor
public enum ResponseCodeEnum {
	// user
	USER_SIGNUP_SUCCESS(HttpStatus.CREATED, "회원가입 성공"),
	USER_LOGOUT_SUCCESS(HttpStatus.OK, "로그아웃 성공"),
	USER_UPDATE_SUCCESS(HttpStatus.OK, "회원 수정 성공"),
	USER_DELETE_SUCCESS(HttpStatus.OK, "회원 삭제 성공"),
	USER_GET_SUCCESS(HttpStatus.OK, "회원 조회 성공"),
	USER_PASSWORD_UPDATE_SUCCESS(HttpStatus.OK, "회원 비밀번호 수정 성공"),
	USER_PASSWORD_CORRECT(HttpStatus.OK, "패스워드가 일치합니다."),
	// project
	PROJECT_CREATE_SUCCESS(HttpStatus.CREATED, "프로젝트 생성 성공"),
	PROJECT_GET_SUCCESS(HttpStatus.OK, "프로젝트 조회 성공"),
	PROJECT_UPDATE_SUCCESS(HttpStatus.OK, "프로젝트 수정 성공"),
	PROJECT_DELETE_SUCCESS(HttpStatus.OK, "프로젝트 삭제 성공"),
	PROJECT_INVITE_SUCCESS(HttpStatus.OK, "프로젝트 초대 성공"),
	PROJECT_ACCEPT_SUCCESS(HttpStatus.OK, "프로젝트 초대 수락 성공"),
	PROJECT_REFUSE_SUCCESS(HttpStatus.OK, "프로젝트 초대 거절 성공"),
	PROJECT_USER_GET_SUCCESS(HttpStatus.OK, "프로젝트 유저 조회 성공"),
	PROJECT_USER_DELETE_SUCCESS(HttpStatus.OK, "프로젝트 유저 제거 성공"),
	PROJECT_INVITE_GET_SUCCESS(HttpStatus.OK, "프로젝트 초대 조회 성공"),
    // category
    CATEGORY_CREATE_SUCCESS(HttpStatus.OK, "카테고리 생성 성공"),
    CATEGORY_SELECT_SUCCESS(HttpStatus.OK, "카테고리 조회 성공"),
    CATEGORY_UPDATE_SUCCESS(HttpStatus.OK, "카테고리 수정 성공"),
    CATEGORY_DELETE_SUCCESS(HttpStatus.OK, "카테고리 삭제 성공"),
	// section
	SECTION_CREATE_SUCCESS(HttpStatus.CREATED, "섹션 생성 성공"),
	SECTION_GET_SUCCESS(HttpStatus.OK, "섹션 조회 성공"),
	SECTION_UPDATE_SUCCESS(HttpStatus.OK, "섹션 수정 성공"),
	SECTION_DELETE_SUCCESS(HttpStatus.OK, "섹션 삭제 성공"),
    //card
    CARD_CREATE_SUCCESS(HttpStatus.OK, "카드 생성 성공"),
    CARD_SELECT_ALL_SUCCESS(HttpStatus.OK, "카드 전체 조회 성공"),
    CARD_SELECT_SUCCESS(HttpStatus.OK, "섹션에 해당하는 카드 조회 성공"),
    CARD_UPDATE_SUCCESS(HttpStatus.OK, "카드 수정 성공"),
    CARD_DELETE_SUCCESS(HttpStatus.OK, "카드 삭제 성공"),
    CARD_SELECT_DETAIL_SUCCESS(HttpStatus.OK, "카드 상세 조회 성공"),
    CARD_COMPLETE_SUCCESS(HttpStatus.OK, "카드 완료 상태 변경 성공"),
    CARD_PROGRESS_SUCCESS(HttpStatus.OK, "카드 진행 상태 변경 성공"),
    CARD_PROGRESS_SELECT_SUCCESS(HttpStatus.OK, "내 진행 카드 조회 성공"),
    CARD_COMPLETE_SELECT_SUCCESS(HttpStatus.OK, "내 완료 카드 조회 성공"),
    //comment
    COMMENT_CREATE_SUCCESS(HttpStatus.OK, "댓글 생성 성공"),
    COMMENT_SELECT_SUCCESS(HttpStatus.OK, "댓글 조회 성공"),
    COMMENT_UPDATE_SUCCESS(HttpStatus.OK, "댓글 수정 성공"),
    COMMENT_DELETE_SUCCESS(HttpStatus.OK, "댓글 삭제 성공"),
    // notification
    COMMENT_NOTIFICATION_SELECT_SUCCESS(HttpStatus.OK, "댓글 알림 조회 성공");

	private final HttpStatus httpStatus;
	private final String message;
}
