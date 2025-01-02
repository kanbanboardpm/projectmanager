package com.pm.projectmanager.exception;

public class ExceptionTestException extends CommonException {

    /**
     * 예외처리 예시 입니다.
     * @param responseCodeEnum
     */
    public ExceptionTestException(ResponseExceptionEnum responseCodeEnum) {
        super(responseCodeEnum);
    }

}