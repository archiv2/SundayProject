// repository/PlaceRepository.java
package com.sunday.server.repository;

import com.sunday.server.entity.Place;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;

public interface PlaceRepository extends JpaRepository<Place, Long> {

    // 무드 기반 필터링
    List<Place> findByMood(String mood);

    // 무드 없이 전체 조회
    List<Place> findAll();
}