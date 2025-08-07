package com.moyeoit.common.exception;

import org.springframework.http.HttpStatus;

public enum BaseErrorCode implements ErrorCode {
    
    NOT_FOUND("NOT_FOUND_BASE", "요청하신 데이터를 찾을 수 없습니다.", HttpStatus.BAD_REQUEST);

    private final String code;
    private final String message;
    private final HttpStatus httpStatus;

    BaseErrorCode(String code, String message, HttpStatus httpStatus) {
        this.code = code;
        this.message = message;
        this.httpStatus = httpStatus;
    }

    @Override
    public String getCode() {
        return this.code;
    }

    @Override
    public String getMessage() {
        return this.message;
    }

    @Override
    public HttpStatus getHttpStatus() {
        return this.httpStatus;
    }
}