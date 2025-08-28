package com.moyeoit.domain.review.controller;

import com.moyeoit.domain.review.controller.request.DisplayQuestionCreateRequest;
import com.moyeoit.domain.review.controller.response.DisplayQuestionResponse;
import com.moyeoit.domain.review.controller.response.DisplayQuestionResponses;
import com.moyeoit.domain.review.domain.ReviewCategory;
import com.moyeoit.domain.review.domain.ReviewType;
import com.moyeoit.global.response.ApiResponse;
import io.swagger.v3.oas.annotations.Operation;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestParam;

public interface DisplayQuestionAPI {

    @Operation(summary = "리뷰 작성 시 노출되는 질문 조회 API", description = "조건에 따라 리뷰 작성 시 보여질 질문을 조회합니다.")
    ResponseEntity<ApiResponse<DisplayQuestionResponses>> getDisplayQuestion(
            @RequestParam ReviewType reviewType,
            @RequestParam ReviewCategory reviewCategory
    );

    @Operation(summary = "리뷰 작성 시 노출되는 질문 생성 API", description = "조건에 따라 리뷰 작성 시 보여질 질문을 생성합니다.")
    ResponseEntity<ApiResponse<DisplayQuestionResponse>> createDisplayQuestion(
            @RequestBody DisplayQuestionCreateRequest request);

}
