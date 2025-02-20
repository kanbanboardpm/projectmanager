package com.pm.projectmanager.exception;

import com.pm.projectmanager.common.response.ResponseExceptionEnum;

public class CannotDeleteCategoryWithRelatedCardsException extends CommonException {
    public CannotDeleteCategoryWithRelatedCardsException(ResponseExceptionEnum responseExceptionEnum) {
        super(responseExceptionEnum);
    }
}
