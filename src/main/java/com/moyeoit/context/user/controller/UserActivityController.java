package com.moyeoit.context.user.controller;

import com.moyeoit.context.user.controller.request.UserActivityUpdateRequest;
import com.moyeoit.context.user.controller.response.UserActivityResponse;
import com.moyeoit.context.user.service.UserActivityService;
import com.moyeoit.global.auth.argument_resolver.AccessUser;
import com.moyeoit.global.auth.argument_resolver.CurrentUser;
import com.moyeoit.global.response.ApiResponse;
import io.swagger.v3.oas.annotations.Parameter;
import io.swagger.v3.oas.annotations.tags.Tag;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.web.bind.annotation.*;


@RestController
@RequiredArgsConstructor
@Slf4j
@RequestMapping("/v1/user/activity")
@Tag(name = "회원 활동 API", description = "회원 활동 관련 API")
public class UserActivityController {

    private final UserActivityService userActivityService;

    @GetMapping
    public ApiResponse<UserActivityResponse> getUserActivity(@Parameter(hidden = false) @CurrentUser AccessUser accessUser) {
        return ApiResponse.success(userActivityService.getUserActivity(accessUser.getId()));
    }

    @PatchMapping
    public ApiResponse<Void> updateUserActivity(@Valid @RequestBody UserActivityUpdateRequest request,
                                                @Parameter(hidden = true) @CurrentUser AccessUser user) {
        userActivityService.updateUserActivity(user.getId(), request);
        return ApiResponse.success(null);
    }

}