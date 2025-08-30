package com.moyeoit.domain.club.controller;

import com.moyeoit.domain.club.controller.request.ClubActivitySaveRequest;
import com.moyeoit.domain.club.controller.request.ClubProcessSaveRequest;
import com.moyeoit.domain.club.controller.request.ClubRecruitmentSaveRequest;
import com.moyeoit.domain.club.controller.request.ClubSaveRequest;
import com.moyeoit.domain.club.controller.request.ClubScheduleSaveRequest;
import com.moyeoit.domain.club.service.ClubManageService;
import io.swagger.v3.oas.annotations.tags.Tag;
import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/clubs")
@RequiredArgsConstructor
@Tag(name = "동아리 관리 API", description = "동아리의 전반적인 생성 및 정보 업데이트에 관한 API")
public class ClubManageController implements ClubManageAPI {
    private final ClubManageService clubService;

    @PostMapping("/detail")
    public void saveClubDetail(@RequestBody ClubSaveRequest request) {
        clubService.saveClubDetail(request);
    }


    @PostMapping("/activity")
    public void saveClubActivity(@RequestBody ClubActivitySaveRequest request) {
        clubService.saveClubActivity(request);
    }


    @PostMapping("/recruit")
    public void saveClubRecruit(@RequestBody ClubRecruitmentSaveRequest request) {
        clubService.saveClubRecruit(request);
    }


    @PostMapping("/process")
    public void saveProcess(@RequestBody ClubProcessSaveRequest request) {
        clubService.saveProcess(request);
    }


    @PostMapping("/schedule")
    public void saveClubSchedule(@RequestBody ClubScheduleSaveRequest request) {
        clubService.saveClubSchedule(request);
    }
}
