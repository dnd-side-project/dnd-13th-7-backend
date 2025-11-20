package com.moyeoit.domain.user.controller;

import com.moyeoit.domain.file.controller.response.FileUploadRequest;
import com.moyeoit.domain.user.controller.request.ActivateRequest;
import com.moyeoit.domain.user.controller.response.ActivateResponse;
import com.moyeoit.domain.user.service.UserService;
import com.moyeoit.domain.user.service.dto.UserDto;
import com.moyeoit.domain.user.service.dto.UserProfileResponse;
import com.moyeoit.global.auth.argument_resolver.AccessUser;
import com.moyeoit.global.auth.argument_resolver.CurrentUser;
import com.moyeoit.global.response.ApiResponse;
import io.swagger.v3.oas.annotations.Parameter;
import io.swagger.v3.oas.annotations.tags.Tag;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
@RequiredArgsConstructor
@Slf4j
@RequestMapping("/v1/user")
@Tag(name = "회원 API", description = "회원 및 마이페이지 관련 API")
public class UserController {

    private final UserService userService;

    @GetMapping("/me")
    public ResponseEntity<String> getMe(@CurrentUser AccessUser user) {
        return ResponseEntity.ok(user.getName());
    }

    /**
     * 해당 유저의 활성 상태를 응답합니다.
     */
    @GetMapping("/activate/{userId}")
    public ApiResponse<ActivateResponse> isActivateUser(@PathVariable Long userId) {
        ActivateResponse response = userService.getActivateStatus(userId);
        return ApiResponse.success(response);
    }

    /**
     * 유저 정보를 조회합니다.
     */
    @GetMapping("/{userId}")
    public ApiResponse<UserDto> getUser(@PathVariable Long userId) {
        log.info("userId {}", userId);
        UserDto user = userService.getUser(userId);
        return ApiResponse.success(user);
    }

    /**
     * 접근한 유저를 활성 상태로 변경합니다.
     */
    @PostMapping("/activate")
    public ApiResponse<UserDto> activateUser(@Parameter(hidden = true) @CurrentUser AccessUser accessUser,
                                             @RequestBody ActivateRequest request) {
        UserDto user = userService.activateUser(accessUser.getId(), request);
        return ApiResponse.success(user);
    }

    /**
     * 유저 프로필 사진 업데이트 API
     */
    @PostMapping("/profile/image")
    public ApiResponse<UserDto> uploadProfileImage(@RequestBody FileUploadRequest request,
                                                   @Parameter(hidden = true) @CurrentUser AccessUser user) {
        UserDto userDto = userService.updateProfileImage(user.getId(), request.getFileUrl());
        return ApiResponse.success(userDto);
    }

    /**
     * 내 정보 조회 API
     */
    @GetMapping("/profile")
    public ApiResponse<UserProfileResponse> getProfile(@CurrentUser AccessUser user) {
        return ApiResponse.success(userService.getProfile(user.getId()));
    }

    /**
     * 관심 활동 조회 API (동아리 구독 수, 리뷰 좋아요 개수)
     */
//    @GetMapping("/interests")
//    public ResponseEntity<ApiResponse<InterestsResponse>> getInterests(
//            @Parameter(hidden = true) @CurrentUser AccessUser user) {
//        InterestsResponse response = appUserService.getInterests(user.getId());
//        return ResponseEntity.ok(ApiResponse.success(response));
//    }

//    @GetMapping("/review")
//    public ResponseEntity<ApiResponse<Page<ReviewResponse>>> getReview(@ModelAttribute MyReviewSearchRequest request,
//                                                                       @ParameterObject Pageable pageable,
//                                                                       @Parameter(hidden = true) @CurrentUser AccessUser user) {
//        Page<ReviewResponse> response = reviewService.getReview(request, user.getId(), pageable);
//        return ResponseEntity.ok(ApiResponse.success(response));
//    }

}