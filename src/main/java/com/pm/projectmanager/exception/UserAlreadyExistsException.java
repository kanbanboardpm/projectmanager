package com.pm.projectmanager.exception;

import com.pm.projectmanager.common.response.ResponseExceptionEnum;

public class UserAlreadyExistsException extends CommonException {

	public UserAlreadyExistsException(ResponseExceptionEnum responseExceptionEnum) {
		super(responseExceptionEnum);
	}

}