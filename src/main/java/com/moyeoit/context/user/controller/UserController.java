package com.moyeoit.context.user.controller;

import com.moyeoit.context.file.controller.response.FileUploadRequest;
import com.moyeoit.context.community.infra.query.PostQueryRepository;
import com.moyeoit.context.community.presentation.controller.response.PostCardResponse;
import com.moyeoit.context.user.controller.request.AccountManageUpdateRequest;
import com.moyeoit.context.user.controller.request.ActivateRequest;
import com.moyeoit.context.user.controller.request.UserUpdateRequest;
import com.moyeoit.context.user.controller.response.ActivateResponse;
import com.moyeoit.context.user.controller.response.UserManageResponse;
import com.moyeoit.context.user.service.UserService;
import com.moyeoit.context.user.service.dto.UserDto;
import com.moyeoit.context.user.service.dto.UserProfileResponse;
import com.moyeoit.global.auth.argument_resolver.AccessUser;
import com.moyeoit.global.auth.argument_resolver.CurrentUser;
import com.moyeoit.global.response.ApiResponse;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
import org.springframework.data.web.PageableDefault;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
@RequiredArgsConstructor
@Slf4j
@RequestMapping("/v1/user")
public class UserController implements UserAPI {

    private final UserService userService;
    private final PostQueryRepository postQueryRepository;

    @Override
    @GetMapping("/me")
    public ResponseEntity<String> getMe(@CurrentUser AccessUser user) {
        return ResponseEntity.ok(user.getName());
    }

    /**
     * 해당 유저의 활성 상태를 응답합니다.
     */
    @Override
    @GetMapping("/activate/{userId}")
    public ApiResponse<ActivateResponse> isActivateUser(@PathVariable Long userId) {
        ActivateResponse response = userService.getActivateStatus(userId);
        return ApiResponse.success(response);
    }

    /**
     * 유저 정보를 조회합니다.
     */
    @Override
    @GetMapping("/{userId}")
    public ApiResponse<UserDto> getUser(@PathVariable Long userId) {
        UserDto user = userService.getUser(userId);
        return ApiResponse.success(user);
    }

    /**
     * 접근한 유저를 활성 상태로 변경합니다.
     */
    @Override
    @PostMapping("/activate")
    public ApiResponse<UserDto> activateUser(@CurrentUser AccessUser accessUser,
                                             @RequestBody ActivateRequest request) {
        UserDto user = userService.activateUser(accessUser.getId(), request);
        return ApiResponse.success(user);
    }

    /**
     * 유저 프로필 사진 업데이트 API
     */
    @Override
    @PostMapping("/profile/image")
    public ApiResponse<UserDto> uploadProfileImage(@RequestBody FileUploadRequest request,
                                                   @CurrentUser AccessUser user) {
        UserDto userDto = userService.updateProfileImage(user.getId(), request.getFileUrl());
        return ApiResponse.success(userDto);
    }

    /**
     * 내 정보 조회 API
     */
    @Override
    @GetMapping("/profile")
    public ApiResponse<UserProfileResponse> getProfile(@CurrentUser AccessUser user) {
        return ApiResponse.success(userService.getProfile(user.getId()));
    }

    /**
     * 계정 관리 정보 조회
     */
    @Override
    @GetMapping("/manage")
    public ApiResponse<UserManageResponse> getManageInfo(@CurrentUser AccessUser user) {
        return ApiResponse.success(userService.getUserManagerInfo(user.getId()));
    }

    /**
     * 계정 관리
     */
    @Override
    @PatchMapping("/manage")
    public ApiResponse<?> updateAccount(@RequestBody AccountManageUpdateRequest req,
                                        @CurrentUser AccessUser user) {
        userService.updateAccount(req, user.getId());
        return ApiResponse.success(null);
    }

    /**
     * 기본 정보 수정
     */
    @Override
    @PatchMapping
    public ApiResponse<?> updateUserInfo(@RequestBody UserUpdateRequest request,
                                         @CurrentUser AccessUser user) {
        userService.updateUserInfo(request, user.getId());
        return ApiResponse.success(null);
    }

    /**
     * 내 작성글 조회 API
     */
    @Override
    @GetMapping("/posts")
    public ApiResponse<Page<PostCardResponse>> getMyPosts(
            @CurrentUser AccessUser user,
            @PageableDefault(size = 4, sort = "createdAt", direction = Sort.Direction.DESC) Pageable pageable
    ) {
        return ApiResponse.success(postQueryRepository.findMyPosts(user.getId(), pageable));
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
