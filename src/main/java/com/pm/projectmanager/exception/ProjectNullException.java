package com.pm.projectmanager.exception;

import com.pm.projectmanager.common.response.ResponseExceptionEnum;

public class ProjectNullException extends CommonException {

	public ProjectNullException(ResponseExceptionEnum responseCodeEnum) {
		super(responseCodeEnum);
	}
}
