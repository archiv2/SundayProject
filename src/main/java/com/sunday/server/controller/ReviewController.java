// controller/ReviewController.java
package com.sunday.server.controller;

import com.sunday.server.common.ApiResponse;
import com.sunday.server.dto.review.CreateReviewRequest;
import com.sunday.server.dto.review.ReviewResponse;
import com.sunday.server.service.ReviewService;
import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/reviews")
@RequiredArgsConstructor
public class ReviewController {

    private final ReviewService reviewService;

    /**
     * 리뷰 작성
     * POST /api/reviews
     */
    @PostMapping
    public ApiResponse<ReviewResponse> createReview(
            @RequestBody CreateReviewRequest request) {
        return ApiResponse.success(reviewService.createReview(request));
    }

    /**
     * 장소별 리뷰 조회
     * GET /api/reviews/place/{placeId}
     */
    @GetMapping("/place/{placeId}")
    public ApiResponse<List<ReviewResponse>> getReviewsByPlace(
            @PathVariable Long placeId) {
        return ApiResponse.success(reviewService.getReviewsByPlace(placeId));
    }

    /**
     * 유저별 리뷰 조회
     * GET /api/reviews/user/{userId}
     */
    @GetMapping("/user/{userId}")
    public ApiResponse<List<ReviewResponse>> getReviewsByUser(
            @PathVariable Long userId) {
        return ApiResponse.success(reviewService.getReviewsByUser(userId));
    }
}