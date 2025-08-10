package com.moyeoit.app_user.controller;

import com.moyeoit.app_user.service.AppUserService;
import com.moyeoit.app_user.service.dto.AppUserDto;
import com.moyeoit.common.response.ApiResponse;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequiredArgsConstructor
@Slf4j
@RequestMapping("/api/v1/user")
public class AppUserController {

    private final AppUserService appUserService;

    /**
     * 토큰 기반의 프로필 정보를 조회합니다.
     */
//    @GetMapping("/me")
//    public ResponseEntity<?> getMe() {
//
//    }
    @GetMapping("/{userId}")
    public ResponseEntity<ApiResponse> getUser(@PathVariable Long userId) {
        AppUserDto appUserDto = appUserService.getAppUser(userId);
        return ResponseEntity.ok(ApiResponse.success("HELLO APP_USER" + appUserDto.getEmail(), appUserDto));
    }

}