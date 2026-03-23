// dto/review/ReviewResponse.java
package com.sunday.server.dto.review;

import com.sunday.server.entity.Review;
import lombok.Builder;
import lombok.Getter;

@Getter
@Builder
public class ReviewResponse {

    private Long id;
    private String authorNickname;
    private String mood;
    private String comment;      // 한줄평
    private String createdAt;

    public static ReviewResponse from(Review review) {
        return ReviewResponse.builder()
                .id(review.getId())
                .authorNickname(review.getUser().getNickname())
                .mood(review.getMood())
                .comment(review.getComment())
                .createdAt(review.getCreatedAt().toString())
                .build();
    }
}