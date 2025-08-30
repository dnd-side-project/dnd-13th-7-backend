package com.moyeoit.global.exception.code;

import com.moyeoit.global.exception.ErrorCode;
import org.springframework.http.HttpStatus;

public enum ReviewErrorCode implements ErrorCode {

    NOT_FOUND("NOT_FOUND_REVIEW", "후기를 찾을 수 없습니다.", HttpStatus.BAD_REQUEST),
    NOT_FOUND_TYPE("NOT_FOUND_TYPE", "후기 타입을 찾을 수 없습니다.", HttpStatus.BAD_REQUEST),
    NOT_FOUND_LIKE("NOT_FOUND_LIKE", "후기 좋아요를 찾을 수 없습니다.", HttpStatus.NOT_FOUND);

    private final String code;
    private final String message;
    private final HttpStatus httpStatus;

    ReviewErrorCode(String code, String message, HttpStatus httpStatus) {
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
