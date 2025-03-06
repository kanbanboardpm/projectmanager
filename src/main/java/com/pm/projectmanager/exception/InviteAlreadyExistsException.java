package com.pm.projectmanager.exception;

import com.pm.projectmanager.common.response.ResponseExceptionEnum;

public class InviteAlreadyExistsException extends CommonException {
	public InviteAlreadyExistsException(ResponseExceptionEnum exception) {
		super(exception);

	}
}
