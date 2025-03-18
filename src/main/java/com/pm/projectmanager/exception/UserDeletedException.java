package com.pm.projectmanager.exception;

import com.pm.projectmanager.common.response.ResponseExceptionEnum;

public class UserDeletedException extends CommonException {
	public UserDeletedException(ResponseExceptionEnum exception) {
		super(exception);
	}
}
