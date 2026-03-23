// dto/auth/AuthResponse.java
package com.sunday.server.dto.auth;

import com.sunday.server.entity.User;
import lombok.Builder;
import lombok.Getter;

@Getter
@Builder
public class AuthResponse {

    private Long userId;
    private String nickname;
    private String profileImageUrl;
    private String token;   // 세션 토큰 or JWT (지금은 userId 기반 simple token) *추후주석수정

    public static AuthResponse from(User user, String token) {
        return AuthResponse.builder()
                .userId(user.getId())
                .nickname(user.getNickname())
                .profileImageUrl(user.getProfileImageUrl())
                .token(token)
                .build();
    }
}