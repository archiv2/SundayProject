// repository/StampRepository.java
package com.sunday.server.repository;

import com.sunday.server.entity.Stamp;
import org.springframework.data.jpa.repository.JpaRepository;

import java.time.LocalDateTime;
import java.util.List;

public interface StampRepository extends JpaRepository<Stamp, Long> {

    // 유저의 전체 스탬프 개수
    int countByUserId(Long userId);

    // 특정 기간 스탬프 목록
    List<Stamp> findByUserIdAndEarnedAtBetween(
            Long userId, LocalDateTime from, LocalDateTime to);

    // 유저의 전체 스탬프 목록
    List<Stamp> findByUserIdOrderByEarnedAtDesc(Long userId);
}