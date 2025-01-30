package com.pm.projectmanager.exception;

import com.pm.projectmanager.common.response.ResponseExceptionEnum;

public class CategoryNameAlreadyExistsInProjectException extends CommonException {
    public CategoryNameAlreadyExistsInProjectException(ResponseExceptionEnum responseExceptionEnum) {
        super(responseExceptionEnum);
    }
}
