// controller/PlaceController.java
package com.sunday.server.controller;

import com.sunday.server.common.ApiResponse;
import com.sunday.server.dto.place.PlaceDetailResponse;
import com.sunday.server.dto.place.PlaceResponse;
import com.sunday.server.service.PlaceService;
import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/places")
@RequiredArgsConstructor
public class PlaceController {

    private final PlaceService placeService;

    /**
     * 장소 목록 조회 (무드 기반 추천)
     * GET /api/places?mood=담백한&userLat=37.5&userLon=127.0
     */
    @GetMapping
    public ApiResponse<List<PlaceResponse>> getPlaces(
            @RequestParam(required = false) String mood,
            @RequestParam(required = false) Double userLat,
            @RequestParam(required = false) Double userLon) {
        return ApiResponse.success(placeService.getPlaces(mood, userLat, userLon));
    }

    /**
     * 장소 상세 조회
     * GET /api/places/{placeId}?userLat=37.5&userLon=127.0
     */
    @GetMapping("/{placeId}")
    public ApiResponse<PlaceDetailResponse> getPlaceDetail(
            @PathVariable Long placeId,
            @RequestParam(required = false) Double userLat,
            @RequestParam(required = false) Double userLon) {
        return ApiResponse.success(placeService.getPlaceDetail(placeId, userLat, userLon));
    }
}