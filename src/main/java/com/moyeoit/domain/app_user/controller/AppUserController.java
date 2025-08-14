package com.moyeoit.domain.app_user.controller;

import com.moyeoit.domain.app_user.controller.request.ActivateRequest;
import com.moyeoit.domain.app_user.service.AppUserService;
import com.moyeoit.domain.app_user.service.dto.AppUserDto;
import com.moyeoit.global.auth.argument_resolver.AccessUser;
import com.moyeoit.global.auth.argument_resolver.CurrentUser;
import com.moyeoit.global.response.ApiResponse;
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

    @GetMapping("/{userId}")
    public ResponseEntity<ApiResponse> getUser(@PathVariable Long userId) {
        AppUserDto appUserDto = appUserService.getAppUser(userId);
        return ResponseEntity.ok(ApiResponse.success("HELLO APP_USER" + appUserDto.getEmail(), appUserDto));
    }


    @PostMapping("/activate")
    public ResponseEntity<?> activateUser(@CurrentUser AccessUser accessUser,
                                          @RequestBody ActivateRequest request) {
        appUserService.activateUser(accessUser.getId(), request.getNickname(), request.getJobId());
        return ResponseEntity.ok("");
    }

}