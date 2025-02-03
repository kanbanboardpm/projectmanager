package com.pm.projectmanager.exception;

import com.pm.projectmanager.common.response.ResponseExceptionEnum;

public class AuthorityNullException extends CommonException {

	public AuthorityNullException(ResponseExceptionEnum responseExceptionEnum) {
		super(responseExceptionEnum);
	}
}
