package com.moyeoit.domain.app_user.controller;

import com.moyeoit.domain.app_user.controller.request.ActivateRequest;
import com.moyeoit.domain.app_user.controller.response.ActivateResponse;
import com.moyeoit.domain.app_user.controller.response.InterestsResponse;
import com.moyeoit.domain.app_user.service.AppUserService;
import com.moyeoit.domain.app_user.service.dto.AppUserDto;
import com.moyeoit.domain.file.controller.response.FileUploadRequest;
import com.moyeoit.global.auth.argument_resolver.AccessUser;
import com.moyeoit.global.auth.argument_resolver.CurrentUser;
import com.moyeoit.global.response.ApiResponse;
import io.swagger.v3.oas.annotations.Parameter;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequiredArgsConstructor
@Slf4j
@RequestMapping("/v1/user")
public class AppUserController {

    private final AppUserService appUserService;

    @GetMapping("/me")
    public ResponseEntity<String> getMe(@CurrentUser AccessUser user) {
        return ResponseEntity.ok(user.getName());
    }

    /**
     * 해당 유저가 활성 상태를 응답합니다.
     */
    @GetMapping("/activate/{userId}")
    public ResponseEntity<ApiResponse<ActivateResponse>> isActivateUser(@PathVariable Long userId) {
        ActivateResponse response = appUserService.getActivateStatus(userId);
        return ResponseEntity.ok(ApiResponse.success(response));
    }

    @GetMapping("/{userId}")
    public ResponseEntity<ApiResponse> getUser(@PathVariable Long userId) {
        AppUserDto appUserDto = appUserService.getAppUser(userId);
        return ResponseEntity.ok(ApiResponse.success("HELLO APP_USER" + appUserDto.getEmail(), appUserDto));
    }

    @PostMapping("/activate")
    public ResponseEntity<?> activateUser(@Parameter(hidden = true) @CurrentUser AccessUser accessUser,
                                          @RequestBody ActivateRequest request) {
        AppUserDto dto = appUserService.activateUser(accessUser.getId(), request);
        return ResponseEntity.ok("");
    }

    /**
     * 유저 프로필 사진 업데이트 API
     */
    @PostMapping("/profile/image")
    public ResponseEntity<ApiResponse<AppUserDto>> uploadProfileImage(@RequestBody FileUploadRequest request,
                                                                      @Parameter(hidden = true) @CurrentUser AccessUser user) {
        AppUserDto userDto = appUserService.updateProfileImage(user.getId(), request.getFileUrl());
        return ResponseEntity.ok(ApiResponse.success(userDto));
    }

    /**
     * 내 정보 조회 API
     */
    @GetMapping("/profile")
    public ResponseEntity<ApiResponse<AppUserDto>> getProfile(@Parameter(hidden = true) @CurrentUser AccessUser user) {
        AppUserDto appUser = appUserService.getProfile(user.getId());
        return ResponseEntity.ok(ApiResponse.success(appUser));
    }

    /**
     * 관심 활동 조회 API (동아리 구독 수, 리뷰 좋아요 개수)
     */
    @GetMapping("/interests")
    public ResponseEntity<ApiResponse<InterestsResponse>> getInterests(
            @Parameter(hidden = true) @CurrentUser AccessUser user) {
        InterestsResponse response = appUserService.getInterests(user.getId());
        return ResponseEntity.ok(ApiResponse.success(response));
    }

}