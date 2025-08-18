package com.moyeoit.global.exception.code;

import com.moyeoit.global.exception.ErrorCode;
import org.springframework.http.HttpStatus;

public enum UserErrorCode implements ErrorCode {

    NOT_FOUND("NOT_FOUND_USER", "유저를 찾을 수 없습니다.", HttpStatus.BAD_REQUEST),
    ALREADY_EXISTS("ALREADY_EXISTS_USER", "같은 유저가 이미 존재합니다.", HttpStatus.BAD_REQUEST),
    NOT_FOUND_JOB("NOT_FOUND_JOB", "해당 직군을 찾을 수 없습니다.", HttpStatus.BAD_REQUEST),
    NOT_FOUND_TERM("NOT_FOUND_TERM", "해당 유저와 연결된 Term 정보를 찾을 수 없습니다.", HttpStatus.BAD_REQUEST);

    private final String code;
    private final String message;
    private final HttpStatus httpStatus;

    UserErrorCode(String code, String message, HttpStatus httpStatus) {
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