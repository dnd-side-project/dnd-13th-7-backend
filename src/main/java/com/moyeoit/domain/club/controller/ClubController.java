package com.moyeoit.domain.club.controller;

import com.moyeoit.domain.club.service.ClubService;
import com.moyeoit.global.response.club.ClubInfoResponse;
import com.moyeoit.global.response.club.ClubListResponse;
import com.moyeoit.domain.club.controller.request.ClubPagingRequest;
import com.moyeoit.global.response.club.ClubRecruitInfoResponse;
import com.moyeoit.global.response.ApiResponse;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
import org.springframework.data.web.PageableDefault;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequiredArgsConstructor
@RequestMapping("/v1/clubs")
public class ClubController {

    private final ClubService clubService;

    @GetMapping("/{clubId}/details")
    public ApiResponse<ClubInfoResponse> getDetailClubInfo(@PathVariable Long clubId) {
        return ApiResponse.success("동아리 상세 정보 조회에 성공하였습니다.", clubService.findDetailInfo(clubId));
    }

    @GetMapping("/{clubId}/recruits")
    public ApiResponse<ClubRecruitInfoResponse> getRecruitInfo(@PathVariable Long clubId) {
        return ApiResponse.success("동아리 모집정보 조회에 성공하였습니다.", clubService.findRecruitInfo(clubId));
    }

    @GetMapping
    public ApiResponse<Page<ClubListResponse>> getClubList(
            @ModelAttribute ClubPagingRequest request,
            @PageableDefault(size = 12, direction = Sort.Direction.DESC)Pageable pageable){
        return ApiResponse.success("동아리 목록 조회에 성공하였습니다.",clubService.findClubList(request,pageable));
    }
}
