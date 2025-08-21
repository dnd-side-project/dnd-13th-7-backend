package com.moyeoit.global.exception.code;

import com.moyeoit.global.exception.ErrorCode;
import org.springframework.http.HttpStatus;

public enum QuestionErrorCode implements ErrorCode {

    NOT_FOUND("NOT_FOUND_QUESTION", "질문을 찾을 수 없습니다.", HttpStatus.BAD_REQUEST),
    INVALID_SORT("INVALID_SORT_SEQUENCE", "객관식 답변에 대한 정렬 순서가 올바르지 않습니다.", HttpStatus.BAD_REQUEST);
    
    private final String code;
    private final String message;
    private final HttpStatus httpStatus;

    QuestionErrorCode(String code, String message, HttpStatus httpStatus) {
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
