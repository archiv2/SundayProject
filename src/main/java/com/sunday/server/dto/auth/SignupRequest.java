// dto/auth/SignupRequest.java
package com.sunday.server.dto.auth;

import lombok.Getter;
import lombok.NoArgsConstructor;

@Getter
@NoArgsConstructor
public class SignupRequest {

    private String countryCode;
    private String phoneNumber;
    private String nickname;
    private String profileImageUrl;
}