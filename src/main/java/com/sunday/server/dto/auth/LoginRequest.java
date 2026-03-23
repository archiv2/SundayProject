// dto/auth/LoginRequest.java
package com.sunday.server.dto.auth;

import lombok.Getter;
import lombok.NoArgsConstructor;

@Getter
@NoArgsConstructor
public class LoginRequest {

    private String countryCode;
    private String phoneNumber;
    private String verificationCode;  // 6자리 인증번호
}