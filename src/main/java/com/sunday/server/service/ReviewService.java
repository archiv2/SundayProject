// service/ReviewService.java
package com.sunday.server.service;

import com.sunday.server.dto.review.CreateReviewRequest;
import com.sunday.server.dto.review.ReviewResponse;
import com.sunday.server.entity.*;
import com.sunday.server.exception.CustomException;
import com.sunday.server.exception.ErrorCode;
import com.sunday.server.repository.*;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;
import java.util.stream.Collectors;

@Service
@RequiredArgsConstructor
public class ReviewService {

    private final ReviewRepository reviewRepository;
    private final UserRepository userRepository;
    private final PlaceRepository placeRepository;

    /**
     * 리뷰 작성
     */
    @Transactional
    public ReviewResponse createReview(CreateReviewRequest request) {
        User user = userRepository.findById(request.getUserId())
                .orElseThrow(() -> new CustomException(ErrorCode.USER_NOT_FOUND));

        Place place = placeRepository.findById(request.getPlaceId())
                .orElseThrow(() -> new CustomException(ErrorCode.PLACE_NOT_FOUND));

        // 중복 리뷰 체크
        if (reviewRepository.existsByUserIdAndPlaceId(
                request.getUserId(), request.getPlaceId())) {
            throw new CustomException(ErrorCode.REVIEW_ALREADY_EXISTS);
        }

        Review review = reviewRepository.save(Review.builder()
                .user(user)
                .place(place)
                .mood(request.getMood())
                .comment(request.getComment())
                .build());

        return ReviewResponse.from(review);
    }

    /**
     * 장소별 리뷰 목록 조회
     */
    @Transactional(readOnly = true)
    public List<ReviewResponse> getReviewsByPlace(Long placeId) {
        return reviewRepository.findByPlaceIdOrderByCreatedAtDesc(placeId)
                .stream()
                .map(ReviewResponse::from)
                .collect(Collectors.toList());
    }

    /**
     * 유저별 리뷰 목록 조회 (마이페이지용)
     */
    @Transactional(readOnly = true)
    public List<ReviewResponse> getReviewsByUser(Long userId) {
        return reviewRepository.findByUserIdOrderByCreatedAtDesc(userId)
                .stream()
                .map(ReviewResponse::from)
                .collect(Collectors.toList());
    }
}