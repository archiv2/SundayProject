// dto/mypage/MyPageSummaryResponse.java
package com.sunday.server.dto.mypage;

import lombok.Builder;
import lombok.Getter;

import java.util.Map;

@Getter
@Builder
public class MyPageSummaryResponse {

    private String nickname;
    private String profileImageUrl;

    // 기간 필터 기준 집계
    private int visitCount;
    private int stampCount;
    private String representMood;

    // 감정 리포트 - 무드별 비율
    // ex) { "담백한": 40, "감각적인": 30, "활기찬": 20, "일요일픽": 10 }
    private Map<String, Integer> moodRatio;
}