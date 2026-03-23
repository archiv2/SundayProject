// dto/course/CourseResponse.java
package com.sunday.server.dto.course;

import com.sunday.server.entity.Course;
import com.sunday.server.entity.CoursePlace;
import lombok.Builder;
import lombok.Getter;

import java.time.LocalDate;
import java.util.List;
import java.util.stream.Collectors;

@Getter
@Builder
public class CourseResponse {

    private Long courseId;
    private String mood;
    private LocalDate date;
    private boolean completed;
    private List<CoursePlaceInfo> places;

    @Getter
    @Builder
    public static class CoursePlaceInfo {
        private int slotOrder;
        private Long placeId;
        private String placeName;
        private String imageUrl;
        private Integer healingScore;
    }

    public static CourseResponse from(Course course, List<CoursePlace> coursePlaces) {
        List<CoursePlaceInfo> placeInfos = coursePlaces.stream()
                .map(cp -> CoursePlaceInfo.builder()
                        .slotOrder(cp.getSlotOrder())
                        .placeId(cp.getPlace().getId())
                        .placeName(cp.getPlace().getName())
                        .imageUrl(cp.getPlace().getImageUrl())
                        .healingScore(cp.getPlace().getHealingScore())
                        .build())
                .collect(Collectors.toList());

        return CourseResponse.builder()
                .courseId(course.getId())
                .mood(course.getMood())
                .date(course.getDate())
                .completed(course.isCompleted())
                .places(placeInfos)
                .build();
    }
}