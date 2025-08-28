package com.moyeoit.domain.club.controller;

import com.moyeoit.domain.club.controller.request.ClubActivitySaveRequest;
import com.moyeoit.domain.club.controller.request.ClubProcessSaveRequest;
import com.moyeoit.domain.club.controller.request.ClubRecruitmentSaveRequest;
import com.moyeoit.domain.club.controller.request.ClubSaveRequest;
import com.moyeoit.domain.club.controller.request.ClubScheduleSaveRequest;
import io.swagger.v3.oas.annotations.Operation;
import org.springframework.web.bind.annotation.RequestBody;

public interface ClubManageAPI {

    @Operation(summary = "동아리 상세 정보 생성 API", description = "동아리 상세 정보를 생성합니다.")
    void saveClubDetail(@RequestBody ClubSaveRequest request);

    @Operation(summary = "동아리 활동 생성 API", description = "동아리 활동 정보를 생성합니다.")
    void saveClubActivity(@RequestBody ClubActivitySaveRequest request);

    @Operation(summary = "동아리 모집공고 생성 API", description = "동아리 모집 공고 정보를 생성합니다.")
    void saveClubRecruit(@RequestBody ClubRecruitmentSaveRequest request);

    @Operation(summary = "동아리 과정 생성 API", description = "동아리 과정 정보를 생성합니다.")
    void saveProcess(@RequestBody ClubProcessSaveRequest request);

    @Operation(summary = "동아리 스케줄 생성 API", description = "동아리 스케줄 정보를 생성합니다.")
    void saveClubSchedule(@RequestBody ClubScheduleSaveRequest request);

}
