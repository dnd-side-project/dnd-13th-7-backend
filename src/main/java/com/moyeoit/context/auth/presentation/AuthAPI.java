package com.moyeoit.context.auth.presentation;

import com.moyeoit.context.auth.presentation.request.AuthRequest;
import com.moyeoit.context.auth.presentation.response.AuthResponse;
import com.moyeoit.context.auth.presentation.response.AuthorizationUriResponse;
import com.moyeoit.context.user.domain.AuthProvider;
import com.moyeoit.global.response.ApiResponse;
import io.swagger.v3.oas.annotations.Operation;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestParam;

public interface AuthAPI {

    @Operation(summary = "인증 URL 조회", description = "OAuth Provider에 맞는 인증 URL을 조회합니다.")
    ResponseEntity<ApiResponse<AuthorizationUriResponse>> authorize(@RequestParam(name = "redirect_uri") String redirectUri,
                                                                    @RequestParam String state,
                                                                    @PathVariable AuthProvider provider);

    @Operation(summary = "로그인 API", description = "OAuth Provider로 부터 발급받은 authentication_code를 통해 로그인을 시도합니다.")
    ResponseEntity<AuthResponse> login(@RequestBody AuthRequest request);

}
