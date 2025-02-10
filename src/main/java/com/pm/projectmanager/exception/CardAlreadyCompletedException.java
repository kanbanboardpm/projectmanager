package com.pm.projectmanager.exception;

import com.pm.projectmanager.common.response.ResponseExceptionEnum;

public class CardAlreadyCompletedException extends CommonException{

    public CardAlreadyCompletedException(ResponseExceptionEnum responseExceptionEnum) {
        super(responseExceptionEnum);
    }
}
