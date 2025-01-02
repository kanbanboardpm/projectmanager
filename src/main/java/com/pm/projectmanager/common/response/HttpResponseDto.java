package com.pm.projectmanager.common.response;

import lombok.Getter;

@Getter
public class HttpResponseDto {

	private Integer statusCode;
	private String message;
	private Object data;

	public HttpResponseDto(Integer statusCode, String message) {
		this.statusCode = statusCode;
		this.message = message;
	}

	public HttpResponseDto(Integer statusCode, String message, Object data) {
		this.statusCode = statusCode;
		this.message = message;
		this.data = data;
	}
}
