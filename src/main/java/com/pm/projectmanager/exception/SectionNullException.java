package com.pm.projectmanager.exception;

import com.pm.projectmanager.common.response.ResponseExceptionEnum;

public class SectionNullException extends CommonException {
	public SectionNullException(ResponseExceptionEnum responseExceptionEnum) {
		super(responseExceptionEnum);

	}
}
