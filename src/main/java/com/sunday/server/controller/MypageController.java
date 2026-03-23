// controller/MypageController.java
package com.sunday.server.controller;

import com.sunday.server.common.ApiResponse;
import com.sunday.server.dto.mypage.MypageRecordResponse;
import com.sunday.server.dto.mypage.MyPageSummaryResponse;
import com.sunday.server.service.MypageService;
import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/api/mypage")
@RequiredArgsConstructor
public class MypageController {

    private final MypageService mypageService;

    /**
     * 마이페이지 요약
     * GET /api/mypage/{userId}/summary?period=weekly
     * period: weekly | monthly | total
     */
    @GetMapping("/{userId}/summary")
    public ApiResponse<MyPageSummaryResponse> getSummary(
            @PathVariable Long userId,
            @RequestParam(defaultValue = "total") String period) {
        return ApiResponse.success(mypageService.getSummary(userId, period));
    }

    /**
     * 마이페이지 기록 상세
     * GET /api/mypage/{userId}/records?period=weekly
     */
    @GetMapping("/{userId}/records")
    public ApiResponse<MypageRecordResponse> getRecords(
            @PathVariable Long userId,
            @RequestParam(defaultValue = "total") String period) {
        return ApiResponse.success(mypageService.getRecords(userId, period));
    }
}