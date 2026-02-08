package com.moyeoit.global.exception.code;

import com.moyeoit.global.exception.ErrorCode;
import org.springframework.http.HttpStatus;

public enum AuthErrorCode implements ErrorCode {

    FAILED_GET_TOKEN("FAILED_GET_TOKEN", "액세스 토큰 요청에 실패하였습니다. code를 확인해주세요.", HttpStatus.BAD_REQUEST),
    FAILED_GET_USER_INFO("FAILED_GET_USER_INFO", "유저 정보를 조회하는데에 실패하였습니다.", HttpStatus.BAD_REQUEST);

    private final String code;
    private final String message;
    private final HttpStatus httpStatus;

    AuthErrorCode(String code, String message, HttpStatus httpStatus) {
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