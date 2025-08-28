package com.moyeoit.domain.review.controller;

import com.moyeoit.domain.review.controller.request.DisplayQuestionCreateRequest;
import com.moyeoit.domain.review.controller.response.DisplayQuestionResponse;
import com.moyeoit.domain.review.controller.response.DisplayQuestionResponses;
import com.moyeoit.domain.review.domain.ReviewCategory;
import com.moyeoit.domain.review.domain.ReviewType;
import com.moyeoit.domain.review.service.DisplayQuestionService;
import com.moyeoit.global.response.ApiResponse;
import io.swagger.v3.oas.annotations.tags.Tag;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/v1/display-question")
@RequiredArgsConstructor
@Slf4j
@Tag(name = "리뷰 작성 질문 노출 API", description = "일반/프리미엄 후기 작성 시 보여지는 질문을 조회하고 생성하는 API 입니다.")

public class DisplayQuestionController implements DisplayQuestionAPI {

    private final DisplayQuestionService displayQuestionService;

    @GetMapping
    public ResponseEntity<ApiResponse<DisplayQuestionResponses>> getDisplayQuestion(
            @RequestParam ReviewType reviewType,
            @RequestParam ReviewCategory reviewCategory
    ) {
        DisplayQuestionResponses responses = displayQuestionService.getDisplayQuestions(reviewType, reviewCategory);
        return ResponseEntity.ok(ApiResponse.success(responses));
    }

    @PostMapping
    public ResponseEntity<ApiResponse<DisplayQuestionResponse>> createDisplayQuestion(
            @RequestBody DisplayQuestionCreateRequest request) {
        DisplayQuestionResponse response = displayQuestionService.createDisplayQuestion(request);
        return ResponseEntity.ok(ApiResponse.success(response));
    }
}
