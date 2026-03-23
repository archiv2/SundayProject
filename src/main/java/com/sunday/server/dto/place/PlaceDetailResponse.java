// dto/place/PlaceDetailResponse.java
package com.sunday.server.dto.place;

import com.sunday.server.dto.review.ReviewResponse;
import com.sunday.server.entity.Place;
import lombok.Builder;
import lombok.Getter;

import java.util.List;

@Getter
@Builder
public class PlaceDetailResponse {

    private Long id;
    private String name;
    private String mood;
    private String imageUrl;
    private String tags;
    private String recommendTarget;
    private Integer healingScore;
    private String address;
    private Double latitude;
    private Double longitude;
    private Double distanceKm;

    private List<ReviewResponse> bestReviews; // 상위 3개

    public static PlaceDetailResponse from(Place place, Double distanceKm, List<ReviewResponse> bestReviews) {
        return PlaceDetailResponse.builder()
                .id(place.getId())
                .name(place.getName())
                .mood(place.getMood())
                .imageUrl(place.getImageUrl())
                .tags(place.getTags())
                .recommendTarget(place.getRecommendTarget())
                .healingScore(place.getHealingScore())
                .address(place.getAddress())
                .latitude(place.getLatitude())
                .longitude(place.getLongitude())
                .distanceKm(distanceKm)
                .bestReviews(bestReviews)
                .build();
    }
}