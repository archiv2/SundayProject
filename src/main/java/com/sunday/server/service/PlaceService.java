// service/PlaceService.java
package com.sunday.server.service;

import com.sunday.server.dto.place.PlaceDetailResponse;
import com.sunday.server.dto.place.PlaceResponse;
import com.sunday.server.dto.review.ReviewResponse;
import com.sunday.server.entity.Place;
import com.sunday.server.exception.CustomException;
import com.sunday.server.exception.ErrorCode;
import com.sunday.server.repository.PlaceRepository;
import com.sunday.server.repository.ReviewRepository;
import com.sunday.server.util.DistanceUtil;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;
import java.util.stream.Collectors;

@Service
@RequiredArgsConstructor
public class PlaceService {

    private final PlaceRepository placeRepository;
    private final ReviewRepository reviewRepository;
    private final DistanceUtil distanceUtil;

    /**
     * 무드 기반 장소 목록 조회
     * mood가 null이면 전체 조회
     */
    @Transactional(readOnly = true)
    public List<PlaceResponse> getPlaces(String mood, Double userLat, Double userLon) {
        List<Place> places = (mood != null && !mood.isBlank())
                ? placeRepository.findByMood(mood)
                : placeRepository.findAll();

        return places.stream()
                .map(place -> {
                    Double distanceKm = (userLat != null && userLon != null)
                            ? distanceUtil.calculateKm(userLat, userLon,
                            place.getLatitude(), place.getLongitude())
                            : null;
                    return PlaceResponse.from(place, distanceKm);
                })
                .collect(Collectors.toList());
    }

    /**
     * 장소 상세 조회 (베스트 리뷰 3개 포함)
     */
    @Transactional(readOnly = true)
    public PlaceDetailResponse getPlaceDetail(Long placeId, Double userLat, Double userLon) {
        Place place = placeRepository.findById(placeId)
                .orElseThrow(() -> new CustomException(ErrorCode.PLACE_NOT_FOUND));

        Double distanceKm = (userLat != null && userLon != null)
                ? distanceUtil.calculateKm(userLat, userLon,
                place.getLatitude(), place.getLongitude())
                : null;

        // 최신순 리뷰 중 상위 3개만
        List<ReviewResponse> bestReviews = reviewRepository
                .findByPlaceIdOrderByCreatedAtDesc(placeId)
                .stream()
                .limit(3)
                .map(ReviewResponse::from)
                .collect(Collectors.toList());

        return PlaceDetailResponse.from(place, distanceKm, bestReviews);
    }
}