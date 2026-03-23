// repository/CoursePlaceRepository.java
package com.sunday.server.repository;

import com.sunday.server.entity.CoursePlace;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;

public interface CoursePlaceRepository extends JpaRepository<CoursePlace, Long> {

    // 코스에 속한 장소 목록 (슬롯 순서대로)
    List<CoursePlace> findByCourseIdOrderBySlotOrder(Long courseId);

    // 코스 내 장소 수 (최대 3개 제한용)
    int countByCourseId(Long courseId);

    // 코스 내 특정 슬롯 중복 체크
    boolean existsByCourseIdAndSlotOrder(Long courseId, int slotOrder);
}