// repository/CourseRepository.java
package com.sunday.server.repository;

import com.sunday.server.entity.Course;
import org.springframework.data.jpa.repository.JpaRepository;

import java.time.LocalDate;
import java.util.List;
import java.util.Optional;

public interface CourseRepository extends JpaRepository<Course, Long> {

    // 특정 유저의 특정 날짜 코스 조회
    Optional<Course> findByUserIdAndDate(Long userId, LocalDate date);

    // 유저의 코스 전체 조회 (마이페이지용)
    List<Course> findByUserIdOrderByDateDesc(Long userId);
}