// dto/review/CreateReviewRequest.java
package com.sunday.server.dto.review;

import lombok.Getter;
import lombok.NoArgsConstructor;

@Getter
@NoArgsConstructor
public class CreateReviewRequest {

    private Long userId;
    private Long placeId;
    private Long courseId;    // nullable
    private String mood;      // "담백한" | "감각적인" | "활기찬" | "일요일픽"
    private String comment;   // 한줄평
}