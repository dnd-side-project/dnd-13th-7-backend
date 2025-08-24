package com.moyeoit.domain.review.controller;

import com.moyeoit.domain.review.controller.request.QuestionCreateRequest;
import com.moyeoit.domain.review.controller.response.QuestionResponse;
import com.moyeoit.domain.review.service.QuestionService;
import com.moyeoit.global.response.ApiResponse;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequiredArgsConstructor
@RequestMapping("/v1/question")
public class QuestionController {

    private final QuestionService questionService;

    @GetMapping("/{questionId}")
    public ResponseEntity<?> getQuestion(@PathVariable Long questionId) {
        QuestionResponse response = questionService.getQuestionById(questionId);
        return ResponseEntity.ok(ApiResponse.success(response));
    }

    @PostMapping
    public ResponseEntity<ApiResponse<QuestionResponse>> createQuestion(@RequestBody QuestionCreateRequest request) {
        QuestionResponse questionResponse = questionService.createQuestion(request);
        return ResponseEntity.ok(ApiResponse.success(questionResponse));
    }

}
