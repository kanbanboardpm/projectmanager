package com.pm.projectmanager.exception;

import com.pm.projectmanager.common.response.ResponseExceptionEnum;

public class UserRoleException extends CommonException {

	public UserRoleException(ResponseExceptionEnum responseExceptionEnum) {
		super(responseExceptionEnum);
	}
}
