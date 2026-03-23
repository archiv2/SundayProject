// service/CourseService.java
package com.sunday.server.service;

import com.sunday.server.dto.course.AddPlaceToCourseRequest;
import com.sunday.server.dto.course.CourseResponse;
import com.sunday.server.dto.course.CreateCourseRequest;
import com.sunday.server.entity.Course;
import com.sunday.server.entity.CoursePlace;
import com.sunday.server.entity.Place;
import com.sunday.server.entity.User;
import com.sunday.server.exception.CustomException;
import com.sunday.server.exception.ErrorCode;
import com.sunday.server.repository.CoursePlaceRepository;
import com.sunday.server.repository.CourseRepository;
import com.sunday.server.repository.PlaceRepository;
import com.sunday.server.repository.UserRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

@Service
@RequiredArgsConstructor
public class CourseService {

    private final CourseRepository courseRepository;
    private final CoursePlaceRepository coursePlaceRepository;
    private final PlaceRepository placeRepository;
    private final UserRepository userRepository;

    /**
     * 코스 생성
     * 같은 날짜에 이미 코스가 있으면 기존 코스 반환
     */
    @Transactional
    public CourseResponse createCourse(CreateCourseRequest request) {
        User user = userRepository.findById(request.getUserId())
                .orElseThrow(() -> new CustomException(ErrorCode.USER_NOT_FOUND));

        // 같은 날짜 코스 이미 있으면 그대로 반환
        Course course = courseRepository
                .findByUserIdAndDate(request.getUserId(), request.getDate())
                .orElseGet(() -> courseRepository.save(
                        Course.builder()
                                .user(user)
                                .mood(request.getMood())
                                .date(request.getDate())
                                .completed(false)
                                .build()
                ));

        List<CoursePlace> coursePlaces =
                coursePlaceRepository.findByCourseIdOrderBySlotOrder(course.getId());

        return CourseResponse.from(course, coursePlaces);
    }

    /**
     * 코스에 장소 추가 (최대 3개)
     */
    @Transactional
    public CourseResponse addPlace(Long courseId, AddPlaceToCourseRequest request) {
        Course course = courseRepository.findById(courseId)
                .orElseThrow(() -> new CustomException(ErrorCode.COURSE_NOT_FOUND));

        // 최대 3개 초과 체크
        if (coursePlaceRepository.countByCourseId(courseId) >= 3) {
            throw new CustomException(ErrorCode.COURSE_FULL);
        }

        // 같은 슬롯에 이미 장소 있으면 덮어쓰기
        if (coursePlaceRepository.existsByCourseIdAndSlotOrder(courseId, request.getSlotOrder())) {
            CoursePlace existing = coursePlaceRepository
                    .findByCourseIdOrderBySlotOrder(courseId)
                    .stream()
                    .filter(cp -> cp.getSlotOrder() == request.getSlotOrder())
                    .findFirst()
                    .orElseThrow();
            coursePlaceRepository.delete(existing);
        }

        Place place = placeRepository.findById(request.getPlaceId())
                .orElseThrow(() -> new CustomException(ErrorCode.PLACE_NOT_FOUND));

        coursePlaceRepository.save(CoursePlace.builder()
                .course(course)
                .place(place)
                .slotOrder(request.getSlotOrder())
                .build());

        List<CoursePlace> coursePlaces =
                coursePlaceRepository.findByCourseIdOrderBySlotOrder(courseId);

        return CourseResponse.from(course, coursePlaces);
    }

    /**
     * 코스 조회
     */
    @Transactional(readOnly = true)
    public CourseResponse getCourse(Long courseId) {
        Course course = courseRepository.findById(courseId)
                .orElseThrow(() -> new CustomException(ErrorCode.COURSE_NOT_FOUND));

        List<CoursePlace> coursePlaces =
                coursePlaceRepository.findByCourseIdOrderBySlotOrder(courseId);

        return CourseResponse.from(course, coursePlaces);
    }

    /**
     * 코스 완료 처리
     */
    @Transactional
    public CourseResponse completeCourse(Long courseId) {
        Course course = courseRepository.findById(courseId)
                .orElseThrow(() -> new CustomException(ErrorCode.COURSE_NOT_FOUND));

        course.complete();

        List<CoursePlace> coursePlaces =
                coursePlaceRepository.findByCourseIdOrderBySlotOrder(courseId);

        return CourseResponse.from(course, coursePlaces);
    }
}