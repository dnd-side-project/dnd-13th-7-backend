package com.moyeoit.context.club.presentation.swagger;

import com.moyeoit.context.club.presentation.request.ClubActivitySaveRequest;
import com.moyeoit.context.club.presentation.request.ClubProcessSaveRequest;
import com.moyeoit.context.club.presentation.request.ClubRecruitmentSaveRequest;
import com.moyeoit.context.club.presentation.request.ClubSaveRequest;
import com.moyeoit.context.club.presentation.request.ClubScheduleSaveRequest;
import io.swagger.v3.oas.annotations.Operation;
import org.springframework.web.bind.annotation.RequestBody;

public interface ClubManageAPI {

    @Deprecated
    @Operation(
            summary = "동아리 상세 정보 생성 API",
            description = "동아리 상세 정보를 생성합니다. (레거시) 대체 엔드포인트: POST /admin/clubs/detail",
            deprecated = true
    )
    void saveClubDetail(@RequestBody ClubSaveRequest request);

    @Deprecated
    @Operation(
            summary = "동아리 활동 생성 API",
            description = "동아리 활동 정보를 생성합니다. (레거시) 대체 엔드포인트: POST /admin/clubs/activity",
            deprecated = true
    )
    void saveClubActivity(@RequestBody ClubActivitySaveRequest request);

    @Deprecated
    @Operation(
            summary = "동아리 모집공고 생성 API",
            description = "동아리 모집 공고 정보를 생성합니다. (레거시) 대체 엔드포인트: POST /admin/clubs/recruit",
            deprecated = true
    )
    void saveClubRecruit(@RequestBody ClubRecruitmentSaveRequest request);

    @Deprecated
    @Operation(
            summary = "동아리 과정 생성 API",
            description = "동아리 과정 정보를 생성합니다. (레거시) 대체 엔드포인트: POST /admin/clubs/process",
            deprecated = true
    )
    void saveProcess(@RequestBody ClubProcessSaveRequest request);

    @Deprecated
    @Operation(
            summary = "동아리 스케줄 생성 API",
            description = "동아리 스케줄 정보를 생성합니다. (레거시) 대체 엔드포인트: POST /admin/clubs/schedule",
            deprecated = true
    )
    void saveClubSchedule(@RequestBody ClubScheduleSaveRequest request);

}
