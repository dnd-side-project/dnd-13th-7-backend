package com.moyeoit.domain.auth.presentation;

import com.moyeoit.domain.auth.application.AuthService;
import com.moyeoit.domain.auth.presentation.request.AuthRequest;
import com.moyeoit.domain.auth.presentation.response.AuthResponse;
import com.moyeoit.domain.auth.presentation.response.AuthorizationUriResponse;
import com.moyeoit.domain.user.domain.AuthProvider;
import com.moyeoit.global.response.ApiResponse;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/api/v1/auth")
@RequiredArgsConstructor
@Slf4j
public class AuthController {

    private final AuthService authService;

    @GetMapping("/{provider}/authorize")
    public ResponseEntity<ApiResponse<AuthorizationUriResponse>> authorize(@RequestParam(name = "redirect_uri") String redirectUri,
                                                                           @RequestParam String state,
                                                                           @PathVariable AuthProvider provider) {

        String url = authService.getAuthorizationUrl(redirectUri, state, provider);
        ApiResponse<AuthorizationUriResponse> response = ApiResponse.success(new AuthorizationUriResponse(url, state, provider));
        return ResponseEntity.ok(response);
    }

    @PostMapping("/login")
    public ResponseEntity<AuthResponse> login(@RequestBody AuthRequest request) {
        AuthResponse response = authService.login(request);
        return ResponseEntity.ok(response);
    }

}
