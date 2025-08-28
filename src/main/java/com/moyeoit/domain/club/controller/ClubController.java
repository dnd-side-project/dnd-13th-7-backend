package com.moyeoit.domain.club.controller;

import com.moyeoit.domain.club.controller.request.ClubPagingRequest;
import com.moyeoit.domain.club.controller.response.ClubFindListResponse;
import com.moyeoit.domain.club.controller.response.ClubInfoResponse;
import com.moyeoit.domain.club.controller.response.ClubListResponse;
import com.moyeoit.domain.club.controller.response.ClubRecruitInfoResponse;
import com.moyeoit.domain.club.service.ClubService;
import com.moyeoit.global.auth.argument_resolver.AccessUser;
import com.moyeoit.global.auth.argument_resolver.CurrentUser;
import com.moyeoit.global.response.ApiResponse;
import io.swagger.v3.oas.annotations.Parameter;
import io.swagger.v3.oas.annotations.tags.Tag;
import java.util.List;
import java.util.Map;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
import org.springframework.data.web.PageableDefault;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequiredArgsConstructor
@RequestMapping("/v1/clubs")
@Tag(name = "동아리 API", description = "동아리 조회 및 구독 등에 대한 기능을 가지는 API")
public class ClubController implements ClubAPI {

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
            @PageableDefault(size = 12, direction = Sort.Direction.DESC) Pageable pageable) {
        return ApiResponse.success("동아리 목록 조회에 성공하였습니다.", clubService.findClubList(request, pageable));
    }

    @GetMapping("/search")
    public ApiResponse<List<ClubFindListResponse>> searchClubList(@RequestParam String keyword) {
        return ApiResponse.success("동아리 검색에 성공하였습니다.", clubService.searchClubList(keyword));
    }

    @PostMapping("/{clubId}/subscribe")
    public ApiResponse<?> subscribeClub(
            @PathVariable Long clubId,
            @Parameter(hidden = true) @CurrentUser AccessUser user) {
        boolean subscribed = clubService.subscribeClub(clubId, user.getId());

        return ApiResponse.success("구독 상태가 변경되었습니다.", Map.of("subscribed", subscribed));
    }

    @GetMapping("/user-subscribe")
    public ApiResponse<?> getUserSubscribe(
            @Parameter(hidden = true) @CurrentUser AccessUser user,
            @PageableDefault(size = 12, direction = Sort.Direction.DESC) Pageable pageable) {
        return ApiResponse.success("유저의 동아리 구독 목록을 확인하였습니다.", clubService.subClubList(user.getId(), pageable));
    }
}
