// exception/ErrorCode.java
package com.sunday.server.exception;

import lombok.AllArgsConstructor;
import lombok.Getter;
import org.springframework.http.HttpStatus;

@Getter
@AllArgsConstructor
public enum ErrorCode {

    // Auth
    DUPLICATE_PHONE_NUMBER(HttpStatus.CONFLICT, "이미 가입된 전화번호입니다."),
    DUPLICATE_NICKNAME(HttpStatus.CONFLICT, "이미 사용 중인 닉네임입니다."),
    USER_NOT_FOUND(HttpStatus.NOT_FOUND, "사용자를 찾을 수 없습니다."),
    VERIFICATION_CODE_NOT_FOUND(HttpStatus.BAD_REQUEST, "인증번호를 먼저 요청해주세요."),
    VERIFICATION_CODE_MISMATCH(HttpStatus.BAD_REQUEST, "인증번호가 일치하지 않습니다."),

    // Place
    PLACE_NOT_FOUND(HttpStatus.NOT_FOUND, "장소를 찾을 수 없습니다."),

    // Course
    COURSE_NOT_FOUND(HttpStatus.NOT_FOUND, "코스를 찾을 수 없습니다."),
    COURSE_FULL(HttpStatus.BAD_REQUEST, "코스에 장소를 더 이상 추가할 수 없습니다. (최대 3개)"),

    // Visit
    ALREADY_VISITED(HttpStatus.CONFLICT, "이미 방문 완료된 장소입니다."),
    NOT_IN_RANGE(HttpStatus.BAD_REQUEST, "도착 인증 가능한 거리가 아닙니다."),

    // Review
    REVIEW_ALREADY_EXISTS(HttpStatus.CONFLICT, "이미 리뷰를 작성하셨습니다."),

    // Common
    INTERNAL_SERVER_ERROR(HttpStatus.INTERNAL_SERVER_ERROR, "서버 오류가 발생했습니다.");

    private final HttpStatus status;
    private final String message;
}