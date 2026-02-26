package com.moyeoit.context.admin.presentation;

import com.moyeoit.context.admin.presentation.swagger.AdminClubManageApi;
import com.moyeoit.context.club.application.service.ClubManageService;
import com.moyeoit.context.club.presentation.request.ClubActivitySaveRequest;
import com.moyeoit.context.club.presentation.request.ClubProcessSaveRequest;
import com.moyeoit.context.club.presentation.request.ClubRecruitmentSaveRequest;
import com.moyeoit.context.club.presentation.request.ClubSaveRequest;
import com.moyeoit.context.club.presentation.request.ClubScheduleSaveRequest;
import io.swagger.v3.oas.annotations.tags.Tag;
import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/admin/clubs")
@RequiredArgsConstructor
@Tag(name = "어드민 동아리 관리 API", description = "동아리의 생성 및 정보 업데이트를 위한 관리자 API")
public class AdminClubManageController implements AdminClubManageApi {

    private final ClubManageService clubManageService;

    @PostMapping("/detail")
    public void saveClubDetail(@RequestBody ClubSaveRequest request) {
        clubManageService.saveClubDetail(request);
    }

    @PostMapping("/activity")
    public void saveClubActivity(@RequestBody ClubActivitySaveRequest request) {
        clubManageService.saveClubActivity(request);
    }

    @PostMapping("/recruit")
    public void saveClubRecruit(@RequestBody ClubRecruitmentSaveRequest request) {
        clubManageService.saveClubRecruit(request);
    }

    @PostMapping("/process")
    public void saveProcess(@RequestBody ClubProcessSaveRequest request) {
        clubManageService.saveProcess(request);
    }

    @PostMapping("/schedule")
    public void saveClubSchedule(@RequestBody ClubScheduleSaveRequest request) {
        clubManageService.saveClubSchedule(request);
    }
}
