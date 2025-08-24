package com.moyeoit.domain.review.controller;

import com.moyeoit.domain.review.controller.request.DisplayQuestionCreateRequest;
import com.moyeoit.domain.review.controller.response.DisplayQuestionResponse;
import com.moyeoit.domain.review.controller.response.DisplayQuestionResponses;
import com.moyeoit.domain.review.domain.ReviewCategory;
import com.moyeoit.domain.review.domain.ReviewType;
import com.moyeoit.domain.review.service.DisplayQuestionService;
import com.moyeoit.global.response.ApiResponse;
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
public class DisplayQuestionController {

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
