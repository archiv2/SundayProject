// dto/place/PlaceResponse.java
package com.sunday.server.dto.place;

import com.sunday.server.entity.Place;
import lombok.Builder;
import lombok.Getter;

@Getter
@Builder
public class PlaceResponse {

    private Long id;
    private String name;
    private String mood;
    private String imageUrl;
    private Integer healingScore;
    private String address;

    // 거리 정보 (서비스에서 계산해서 주입)
    private Double distanceKm;

    public static PlaceResponse from(Place place, Double distanceKm) {
        return PlaceResponse.builder()
                .id(place.getId())
                .name(place.getName())
                .mood(place.getMood())
                .imageUrl(place.getImageUrl())
                .healingScore(place.getHealingScore())
                .address(place.getAddress())
                .distanceKm(distanceKm)
                .build();
    }
}