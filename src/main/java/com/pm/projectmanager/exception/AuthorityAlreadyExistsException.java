package com.pm.projectmanager.exception;

import com.pm.projectmanager.common.response.ResponseExceptionEnum;

public class AuthorityAlreadyExistsException extends CommonException {
	public AuthorityAlreadyExistsException(ResponseExceptionEnum responseCodeEnum) {
		super(responseCodeEnum);
	}
}
