package com.moyeoit.context.user.controller;

import com.moyeoit.context.community.presentation.controller.response.PostCardResponse;
import com.moyeoit.context.file.controller.response.FileUploadRequest;
import com.moyeoit.context.user.controller.request.AccountManageUpdateRequest;
import com.moyeoit.context.user.controller.request.ActivateRequest;
import com.moyeoit.context.user.controller.request.UserUpdateRequest;
import com.moyeoit.context.user.controller.response.ActivateResponse;
import com.moyeoit.context.user.controller.response.UserManageResponse;
import com.moyeoit.context.user.service.dto.UserDto;
import com.moyeoit.context.user.service.dto.UserProfileResponse;
import com.moyeoit.global.auth.argument_resolver.AccessUser;
import com.moyeoit.global.response.ApiResponse;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.Parameter;
import io.swagger.v3.oas.annotations.Parameters;
import io.swagger.v3.oas.annotations.security.SecurityRequirement;
import io.swagger.v3.oas.annotations.tags.Tag;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestBody;

@Tag(name = "회원 API", description = "회원 및 마이페이지 관련 API")
public interface UserAPI {

    @Operation(summary = "현재 사용자 이름 조회", description = "Access token 기반으로 사용자 이름을 조회합니다.", security = @SecurityRequirement(name = "bearerAuth"))
    ResponseEntity<String> getMe(@Parameter(hidden = true) AccessUser user);

    @Operation(summary = "활성 유저 여부 조회", description = "회원 ID를 기반으로 활성 유저 여부를 반환합니다.")
    ApiResponse<ActivateResponse> isActivateUser(@PathVariable Long userId);

    @Operation(summary = "유저 정보 조회", description = "회원 ID를 기반으로 유저 정보를 조회합니다.")
    ApiResponse<UserDto> getUser(@PathVariable Long userId);

    @Operation(summary = "유저 활성화", description = "닉네임과 직군을 설정하여 해당 유저의 상태를 활성화합니다.", security = @SecurityRequirement(name = "bearerAuth"))
    ApiResponse<UserDto> activateUser(
            @Parameter(hidden = true) AccessUser accessUser,
            @RequestBody ActivateRequest request
    );

    @Operation(summary = "프로필 이미지 변경", description = "유저 프로필 사진을 변경합니다.", security = @SecurityRequirement(name = "bearerAuth"))
    ApiResponse<UserDto> uploadProfileImage(
            @RequestBody FileUploadRequest request,
            @Parameter(hidden = true) AccessUser user
    );

    @Operation(summary = "내 프로필 조회", description = "로그인한 유저의 프로필 정보를 조회합니다.", security = @SecurityRequirement(name = "bearerAuth"))
    ApiResponse<UserProfileResponse> getProfile(@Parameter(hidden = true) AccessUser user);

    @Operation(summary = "계정 관리 정보 조회", description = "계정 관리 정보를 조회합니다.", security = @SecurityRequirement(name = "bearerAuth"))
    ApiResponse<UserManageResponse> getManageInfo(@Parameter(hidden = true) AccessUser user);

    @Operation(summary = "계정 관리 수정", description = "계정 관리 정보를 수정합니다.", security = @SecurityRequirement(name = "bearerAuth"))
    ApiResponse<?> updateAccount(
            @RequestBody AccountManageUpdateRequest req,
            @Parameter(hidden = true) AccessUser user
    );

    @Operation(summary = "기본 정보 수정", description = "기본 사용자 정보를 수정합니다.", security = @SecurityRequirement(name = "bearerAuth"))
    ApiResponse<?> updateUserInfo(
            @RequestBody UserUpdateRequest request,
            @Parameter(hidden = true) AccessUser user
    );

    @Operation(summary = "내 작성글 조회", description = "로그인한 사용자가 작성한 글을 4개씩 페이징 조회합니다.", security = @SecurityRequirement(name = "bearerAuth"))
    @Parameters({
            @Parameter(name = "page", description = "페이지 번호", example = "0"),
            @Parameter(name = "size", description = "페이지 크기", example = "4"),
            @Parameter(name = "sort", description = "정렬, 예: createdAt,desc")
    })
    ApiResponse<Page<PostCardResponse>> getMyPosts(
            @Parameter(hidden = true) AccessUser user,
            Pageable pageable
    );
}
