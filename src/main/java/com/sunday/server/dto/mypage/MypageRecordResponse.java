// dto/mypage/MypageRecordResponse.java
package com.sunday.server.dto.mypage;

import lombok.Builder;
import lombok.Getter;

import java.util.List;

@Getter
@Builder
public class MypageRecordResponse {

    private int visitCount;       // 쌓인 일요일 (방문 횟수)
    private int stampCount;       // 스탬프 개수
    private String representMood; // 대표 무드 (가장 많은 무드)

    // 방문 장소 + 리뷰 목록
    private List<VisitRecord> records;

    @Getter
    @Builder
    public static class VisitRecord {
        private Long placeId;
        private String placeName;
        private String imageUrl;
        private String mood;
        private String comment;   // 한줄평 (없으면 null)
        private String arrivedAt;
    }
}