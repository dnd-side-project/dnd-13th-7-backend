package com.moyeoit.domain.app_user.controller;

import com.moyeoit.domain.app_user.service.JobService;
import com.moyeoit.domain.app_user.service.dto.JobsDto;
import com.moyeoit.global.response.ApiResponse;
import io.swagger.v3.oas.annotations.tags.Tag;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@Slf4j
@RequiredArgsConstructor
@RequestMapping("/v1/job")
@Tag(name = "직군 API", description = "직군 관련된 API 입니다.")
public class JobController implements JobAPI {

    private final JobService jobService;

    @GetMapping()
    public ResponseEntity<ApiResponse<JobsDto>> getJobs() {
        return ResponseEntity.ok(ApiResponse.success(jobService.getJobs()));

    }

}