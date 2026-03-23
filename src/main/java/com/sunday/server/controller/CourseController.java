// controller/CourseController.java
package com.sunday.server.controller;

import com.sunday.server.common.ApiResponse;
import com.sunday.server.dto.course.AddPlaceToCourseRequest;
import com.sunday.server.dto.course.CourseResponse;
import com.sunday.server.dto.course.CreateCourseRequest;
import com.sunday.server.service.CourseService;
import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/api/courses")
@RequiredArgsConstructor
public class CourseController {

    private final CourseService courseService;

    /**
     * 코스 생성
     * POST /api/courses
     */
    @PostMapping
    public ApiResponse<CourseResponse> createCourse(@RequestBody CreateCourseRequest request) {
        return ApiResponse.success(courseService.createCourse(request));
    }

    /**
     * 코스 조회
     * GET /api/courses/{courseId}
     */
    @GetMapping("/{courseId}")
    public ApiResponse<CourseResponse> getCourse(@PathVariable Long courseId) {
        return ApiResponse.success(courseService.getCourse(courseId));
    }

    /**
     * 코스에 장소 추가
     * POST /api/courses/{courseId}/places
     */
    @PostMapping("/{courseId}/places")
    public ApiResponse<CourseResponse> addPlace(
            @PathVariable Long courseId,
            @RequestBody AddPlaceToCourseRequest request) {
        return ApiResponse.success(courseService.addPlace(courseId, request));
    }

    /**
     * 코스 완료 처리
     * PATCH /api/courses/{courseId}/complete
     */
    @PatchMapping("/{courseId}/complete")
    public ApiResponse<CourseResponse> completeCourse(@PathVariable Long courseId) {
        return ApiResponse.success(courseService.completeCourse(courseId));
    }
}