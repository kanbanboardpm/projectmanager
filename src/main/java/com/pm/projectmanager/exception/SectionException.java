package com.pm.projectmanager.exception;

import com.pm.projectmanager.common.response.ResponseExceptionEnum;

public class SectionException extends CommonException {
	public SectionException(ResponseExceptionEnum responseExceptionEnum) {
		super(responseExceptionEnum);

	}
}
