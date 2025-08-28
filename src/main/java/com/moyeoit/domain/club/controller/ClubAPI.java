package com.moyeoit.domain.club.controller;

import com.moyeoit.domain.club.controller.request.ClubPagingRequest;
import com.moyeoit.domain.club.controller.response.ClubFindListResponse;
import com.moyeoit.domain.club.controller.response.ClubInfoResponse;
import com.moyeoit.domain.club.controller.response.ClubListResponse;
import com.moyeoit.domain.club.controller.response.ClubRecruitInfoResponse;
import com.moyeoit.global.auth.argument_resolver.AccessUser;
import com.moyeoit.global.auth.argument_resolver.CurrentUser;
import com.moyeoit.global.response.ApiResponse;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.Parameter;
import java.util.List;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
import org.springframework.data.web.PageableDefault;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestParam;

public interface ClubAPI {

    @Operation(summary = "동아리 상세 조회 API", description = "동아리 상세 정보를 조회합니다.")
    ApiResponse<ClubInfoResponse> getDetailClubInfo(@PathVariable Long clubId);

    @Operation(summary = "모집 공고 조회 API", description = "모집 공고 조회를 진행합니다.")
    ApiResponse<ClubRecruitInfoResponse> getRecruitInfo(@PathVariable Long clubId);

    @Operation(summary = "동아리 목록 조회 API", description = "동아리 목록을 조회합니다.")
    ApiResponse<Page<ClubListResponse>> getClubList(
            @ModelAttribute ClubPagingRequest request,
            @PageableDefault(size = 12, direction = Sort.Direction.DESC) Pageable pageable);

    @Operation(summary = "동아리 키워드 검색 API", description = "검색어를 통해 동아리를 검색합니다.")
    ApiResponse<List<ClubFindListResponse>> searchClubList(@RequestParam String keyword);

    @Operation(summary = "동아리 구독 API", description = "동아리 구독에 대한 Toggle 작업을 진행합니다.")
    ApiResponse<?> subscribeClub(
            @PathVariable Long clubId,
            @Parameter(hidden = true) @CurrentUser AccessUser user);

    @Operation(summary = "유저 동아리 구독 목록 조회 API", description = "유저의 동아리 구독 목록 정보를 조회합니다.")
    ApiResponse<?> getUserSubscribe(
            @Parameter(hidden = true) @CurrentUser AccessUser user,
            @PageableDefault(size = 12, direction = Sort.Direction.DESC) Pageable pageable);

}
