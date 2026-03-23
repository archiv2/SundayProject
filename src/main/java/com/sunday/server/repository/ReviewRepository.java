// repository/ReviewRepository.java
package com.sunday.server.repository;

import com.sunday.server.entity.Review;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;

public interface ReviewRepository extends JpaRepository<Review, Long> {

    // 장소별 리뷰 최신순 조회
    List<Review> findByPlaceIdOrderByCreatedAtDesc(Long placeId);

    // 사용자 + 장소 조합으로 중복 리뷰 체크
    boolean existsByUserIdAndPlaceId(Long userId, Long placeId);

    // 마이페이지용 - 사용자 리뷰 전체
    List<Review> findByUserIdOrderByCreatedAtDesc(Long userId);
}