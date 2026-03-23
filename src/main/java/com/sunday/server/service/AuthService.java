// service/AuthService.java
package com.sunday.server.service;

import com.sunday.server.dto.auth.AuthResponse;
import com.sunday.server.dto.auth.LoginRequest;
import com.sunday.server.dto.auth.SignupRequest;
import com.sunday.server.entity.User;
import com.sunday.server.exception.CustomException;
import com.sunday.server.exception.ErrorCode;
import com.sunday.server.repository.UserRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.Map;
import java.util.UUID;
import java.util.concurrent.ConcurrentHashMap;

@Service
@RequiredArgsConstructor
public class AuthService {

    private final UserRepository userRepository;

    // 인증번호 임시 저장 (실제 서비스에서는 Redis 사용 권장)
    // key: 전화번호(국가번호 포함), value: 인증번호
    private final Map<String, String> verificationStore = new ConcurrentHashMap<>();

    /**
     * 인증번호 발송
     * 실제 SMS 발송은 외부 서비스(Twilio, NHN Toast 등) 연동 필요
     * 현재는 콘솔 출력으로 대체
     */
    public void sendVerificationCode(String countryCode, String phoneNumber) {
        String fullPhone = countryCode + phoneNumber;
        String code = generateCode();

        verificationStore.put(fullPhone, code);

        // TODO: 실제 SMS 발송 연동
        System.out.println("[SMS] " + fullPhone + " 인증번호: " + code);
    }

    /**
     * 인증번호 검증
     */
    public boolean verifyCode(String countryCode, String phoneNumber, String inputCode) {
        String fullPhone = countryCode + phoneNumber;
        String storedCode = verificationStore.get(fullPhone);

        if (storedCode == null) {
            throw new CustomException(ErrorCode.VERIFICATION_CODE_NOT_FOUND);
        }
        if (!storedCode.equals(inputCode)) {
            throw new CustomException(ErrorCode.VERIFICATION_CODE_MISMATCH);
        }

        // 검증 성공 시 코드 삭제 (1회용)
        verificationStore.remove(fullPhone);
        return true;
    }

    /**
     * 회원가입
     */
    @Transactional
    public AuthResponse signup(SignupRequest request) {
        String fullPhone = request.getCountryCode() + request.getPhoneNumber();

        if (userRepository.existsByPhoneNumber(fullPhone)) {
            throw new CustomException(ErrorCode.DUPLICATE_PHONE_NUMBER);
        }
        if (userRepository.existsByNickname(request.getNickname())) {
            throw new CustomException(ErrorCode.DUPLICATE_NICKNAME);
        }

        User user = User.builder()
                .phoneNumber(fullPhone)
                .nickname(request.getNickname())
                .profileImageUrl(request.getProfileImageUrl())
                .build();

        User savedUser = userRepository.save(user);
        String token = generateToken(savedUser.getId());

        return AuthResponse.from(savedUser, token);
    }

    /**
     * 로그인 (인증번호 검증 후 호출)
     */
    @Transactional(readOnly = true)
    public AuthResponse login(LoginRequest request) {
        String fullPhone = request.getCountryCode() + request.getPhoneNumber();

        // 인증번호 검증
        verifyCode(request.getCountryCode(), request.getPhoneNumber(), request.getVerificationCode());

        User user = userRepository.findByPhoneNumber(fullPhone)
                .orElseThrow(() -> new CustomException(ErrorCode.USER_NOT_FOUND));

        String token = generateToken(user.getId());
        return AuthResponse.from(user, token);
    }

    // --- private helpers ---

    private String generateCode() {
        int code = (int)(Math.random() * 900000) + 100000;
        return String.valueOf(code);
    }

    private String generateToken(Long userId) {
        // 심플 토큰: userId + UUID 조합 (실제 서비스엔 JWT 사용 권장)
        return userId + "_" + UUID.randomUUID().toString().replace("-", "");
    }
}