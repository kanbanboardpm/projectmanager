package com.pm.projectmanager.exception;

import com.pm.projectmanager.common.response.ResponseExceptionEnum;

public class NoInviteException extends CommonException {

	public NoInviteException(ResponseExceptionEnum responseCodeEnum) {
		super(responseCodeEnum);
	}
}
