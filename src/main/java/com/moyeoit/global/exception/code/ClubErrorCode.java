package com.moyeoit.global.exception.code;

import com.moyeoit.global.exception.ErrorCode;
import org.springframework.http.HttpStatus;

public enum ClubErrorCode implements ErrorCode {

    NOT_FOUND("NOT_FOUND_CLUB", "동아리를 찾을 수 없습니다.", HttpStatus.NOT_FOUND),
    NOT_FOUND_KEYWORD("NOT_FOUND_CLUB_KEYWORD", "동아리 검색어를 찾을 수 없습니다.", HttpStatus.NOT_FOUND);
    private final String code;
    private final String message;
    private final HttpStatus httpStatus;

    ClubErrorCode(String code, String message, HttpStatus httpStatus) {
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
