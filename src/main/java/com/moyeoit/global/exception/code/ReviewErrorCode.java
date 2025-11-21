package com.moyeoit.global.exception.code;

import com.moyeoit.global.exception.ErrorCode;
import org.springframework.http.HttpStatus;

public enum ReviewErrorCode implements ErrorCode {

    NOT_FOUND("NOT_FOUND_REVIEW", "리뷰를 찾을 수 없습니다.", HttpStatus.NOT_FOUND),
    NOT_FOUND_TYPE("NOT_FOUND_TYPE", "리뷰 타입을 찾을 수 없습니다.", HttpStatus.NOT_FOUND),
    NOT_FOUND_LIKE("NOT_FOUND_LIKE", "리뷰 좋아요를 찾을 수 없습니다.", HttpStatus.NOT_FOUND),
    NOT_FOUND_REVIEW_COMMENT("NOT_FOUND_REVIEW_COMMENT", "리뷰 댓글을 찾을 수 없습니다.", HttpStatus.BAD_REQUEST),
    INVALID_REVIEW_WRITE_REQUEST("INVALID_REVIEW_WRITE_REQUEST", "올바르지 않은 리뷰 작성이기에, 리뷰 요약을 생성할 수 없습니다. 해당 문제가 지속되면 관리자에게 문의 바랍니다.", HttpStatus.BAD_REQUEST),
    NOT_REVIEW_OWNER("NOT_REVIEW_OWNER", "해당 리뷰의 작성자가 아니기에 작업을 처리할 수 없습니다.", HttpStatus.FORBIDDEN),
    NOT_REVIEW_COMMENT_OWNER("NOT_REVIEW_COMMENT_OWNER", "해당 댓글의 작성자가 아니기에 작업을 처리할 수 없습니다", HttpStatus.FORBIDDEN);


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
