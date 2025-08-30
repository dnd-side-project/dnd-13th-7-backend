package com.moyeoit.domain.app_user.controller;

import com.moyeoit.domain.app_user.controller.request.ActivateRequest;
import com.moyeoit.domain.app_user.controller.response.ActivateResponse;
import com.moyeoit.domain.app_user.controller.response.InterestsResponse;
import com.moyeoit.domain.app_user.service.dto.AppUserDto;
import com.moyeoit.domain.file.controller.response.FileUploadRequest;
import com.moyeoit.domain.review.controller.request.MyReviewSearchRequest;
import com.moyeoit.domain.review.controller.response.ReviewResponse;
import com.moyeoit.global.auth.argument_resolver.AccessUser;
import com.moyeoit.global.auth.argument_resolver.CurrentUser;
import com.moyeoit.global.response.ApiResponse;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.Parameter;
import org.springdoc.core.annotations.ParameterObject;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestBody;

public interface AppUserAPI {

    @Operation(summary = "활성 유저 여부 조회 API", description = "회원 ID를 기반으로 활성 유저 여부를 반환합니다.")
    ResponseEntity<ApiResponse<ActivateResponse>> isActivateUser(@PathVariable Long userId);

    @Operation(summary = "유저 활성 API", description = "닉네임과 직군을 설정하여 해당 유저의 상태를 활성화 합니다.")
    ResponseEntity<?> activateUser(@Parameter(hidden = true)
                                   @CurrentUser AccessUser accessUser,
                                   @RequestBody ActivateRequest request);

    @Operation(summary = "유저 프로필 이미지 등록 변경 API", description = "유저 프로필 사진을 변경합니다.")
    ResponseEntity<ApiResponse<AppUserDto>> uploadProfileImage(@RequestBody FileUploadRequest request,
                                                               @Parameter(hidden = true) @CurrentUser AccessUser user);

    @Operation(summary = "유저 프로필 조회 API", description = "유저 프로필 정보를 조회합니다.")
    ResponseEntity<ApiResponse<AppUserDto>> getProfile(@Parameter(hidden = true) @CurrentUser AccessUser user);

    @Operation(summary = "관심 활동 조회 API", description = "동아리 구독 수, 리뷰 좋아요 개수를 조회합니다.")
    ResponseEntity<ApiResponse<InterestsResponse>> getInterests(@Parameter(hidden = true) @CurrentUser AccessUser user);

    @Operation(summary = "작성 리뷰 조회 API", description = "본인이 작성한 리뷰를 조회합니다.")
    ResponseEntity<ApiResponse<Page<ReviewResponse>>> getReview(@ModelAttribute MyReviewSearchRequest request,
                                                                @ParameterObject Pageable pageable,
                                                                @Parameter(hidden = true) @CurrentUser AccessUser user);
}
