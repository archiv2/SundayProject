// dto/visit/VisitArriveRequest.java
package com.sunday.server.dto.visit;

import lombok.Getter;
import lombok.NoArgsConstructor;

@Getter
@NoArgsConstructor
public class VisitArriveRequest {

    private Long userId;
    private Long placeId;
    private Long courseId;      // nullable (코스 없이 방문 가능)
    private Double userLat;     // 사용자 현재 위도
    private Double userLon;     // 사용자 현재 경도
}