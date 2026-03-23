// controller/VisitController.java
package com.sunday.server.controller;

import com.sunday.server.common.ApiResponse;
import com.sunday.server.dto.visit.VisitArriveRequest;
import com.sunday.server.service.VisitService;
import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/api/visits")
@RequiredArgsConstructor
public class VisitController {

    private final VisitService visitService;

    /**
     * GPS 도착 인증 + 방문 완료
     * POST /api/visits/arrive
     * body: { userId, placeId, courseId, userLat, userLon }
     */
    @PostMapping("/arrive")
    public ApiResponse<String> arrive(@RequestBody VisitArriveRequest request) {
        String placeName = visitService.arrive(request);
        return ApiResponse.success(placeName);
        // 응답 예시: { "success": true, "data": "성수 카페" }
        // 프론트에서 "OOO님, 성수 카페 방문 완료!" 팝업 띄우면 됨
    }
}

