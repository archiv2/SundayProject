// controller/AuthController.java
package com.sunday.server.controller;

import com.sunday.server.common.ApiResponse;
import com.sunday.server.dto.auth.AuthResponse;
import com.sunday.server.dto.auth.LoginRequest;
import com.sunday.server.dto.auth.SignupRequest;
import com.sunday.server.service.AuthService;
import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/api/auth")
@RequiredArgsConstructor
public class AuthController {

    private final AuthService authService;

    /**
     * 인증번호 발송
     * POST /api/auth/send-code
     * body: { "countryCode": "82", "phoneNumber": "01012345678" }
     */
    @PostMapping("/send-code")
    public ApiResponse<Void> sendCode(
            @RequestParam String countryCode,
            @RequestParam String phoneNumber) {
        authService.sendVerificationCode(countryCode, phoneNumber);
        return ApiResponse.success(null);
    }

    /**
     * 인증번호 검증
     * POST /api/auth/verify-code
     */
    @PostMapping("/verify-code")
    public ApiResponse<Boolean> verifyCode(
            @RequestParam String countryCode,
            @RequestParam String phoneNumber,
            @RequestParam String code) {
        boolean result = authService.verifyCode(countryCode, phoneNumber, code);
        return ApiResponse.success(result);
    }

    /**
     * 회원가입
     * POST /api/auth/signup
     */
    @PostMapping("/signup")
    public ApiResponse<AuthResponse> signup(@RequestBody SignupRequest request) {
        return ApiResponse.success(authService.signup(request));
    }

    /**
     * 로그인
     * POST /api/auth/login
     */
    @PostMapping("/login")
    public ApiResponse<AuthResponse> login(@RequestBody LoginRequest request) {
        return ApiResponse.success(authService.login(request));
    }
}