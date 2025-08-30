package com.moyeoit.global.exception;

import com.moyeoit.global.response.ApiResponse;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.RestControllerAdvice;

@RestControllerAdvice
public class GlobalExceptionController {

    @ExceptionHandler(AppException.class)
    public ResponseEntity<ApiResponse<Void>> processApplicationException(AppException e) {
        ErrorCode code = e.getErrorCode();
        return ResponseEntity
                .status(code.getHttpStatus())
                .body(ApiResponse.fail(code.getMessage()));
    }

}