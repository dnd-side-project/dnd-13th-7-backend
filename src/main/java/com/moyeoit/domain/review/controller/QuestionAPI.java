package com.moyeoit.domain.review.controller;

import com.moyeoit.domain.review.controller.request.QuestionCreateRequest;
import com.moyeoit.domain.review.controller.response.QuestionResponse;
import com.moyeoit.global.response.ApiResponse;
import io.swagger.v3.oas.annotations.Operation;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestBody;

public interface QuestionAPI {

    @Operation(summary = "질문 조회 API", description = "Question ID로 질문을 조회합니다.")
    ResponseEntity<?> getQuestion(@PathVariable Long questionId);

    @Operation(summary = "질문 생성 API", description = "질문을 생성합니다.")
    ResponseEntity<ApiResponse<QuestionResponse>> createQuestion(@RequestBody QuestionCreateRequest request);


}
