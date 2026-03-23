// repository/VisitRepository.java
package com.sunday.server.repository;

import com.sunday.server.entity.Visit;
import org.springframework.data.jpa.repository.JpaRepository;

import java.time.LocalDateTime;
import java.util.List;

public interface VisitRepository extends JpaRepository<Visit, Long> {

    // 유저 + 장소 방문 여부 체크
    boolean existsByUserIdAndPlaceId(Long userId, Long placeId);

    // 유저의 방문 목록 (마이페이지용)
    List<Visit> findByUserIdOrderByArrivedAtDesc(Long userId);

    // 특정 기간 방문 목록
    List<Visit> findByUserIdAndArrivedAtBetweenOrderByArrivedAtDesc(
            Long userId, LocalDateTime from, LocalDateTime to);

    // 코스별 방문 목록
    List<Visit> findByCourseId(Long courseId);
}