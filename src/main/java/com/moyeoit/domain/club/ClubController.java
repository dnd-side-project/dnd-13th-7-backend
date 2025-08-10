package com.moyeoit.domain.club;

import com.moyeoit.domain.club.dto.ClubInfoResponse;
import com.moyeoit.domain.club.dto.ClubRecruitInfoResponse;
import com.moyeoit.global.response.ApiResponse;
import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.GetMapping;
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
}
