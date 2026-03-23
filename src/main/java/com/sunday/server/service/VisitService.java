// service/VisitService.java
package com.sunday.server.service;

import com.sunday.server.dto.visit.VisitArriveRequest;
import com.sunday.server.entity.*;
import com.sunday.server.exception.CustomException;
import com.sunday.server.exception.ErrorCode;
import com.sunday.server.repository.*;
import com.sunday.server.util.DistanceUtil;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Service
@RequiredArgsConstructor
public class VisitService {

    private final VisitRepository visitRepository;
    private final UserRepository userRepository;
    private final PlaceRepository placeRepository;
    private final CourseRepository courseRepository;
    private final StampService stampService;
    private final DistanceUtil distanceUtil;

    /**
     * GPS 도착 인증 + 방문 완료 처리 + 스탬프 적립
     */
    @Transactional
    public String arrive(VisitArriveRequest request) {
        User user = userRepository.findById(request.getUserId())
                .orElseThrow(() -> new CustomException(ErrorCode.USER_NOT_FOUND));

        Place place = placeRepository.findById(request.getPlaceId())
                .orElseThrow(() -> new CustomException(ErrorCode.PLACE_NOT_FOUND));

        // 이미 방문한 장소 중복 체크
        if (visitRepository.existsByUserIdAndPlaceId(
                request.getUserId(), request.getPlaceId())) {
            throw new CustomException(ErrorCode.ALREADY_VISITED);
        }

        // GPS 도착 판별 (반경 300m)
        boolean gpsVerified = distanceUtil.isArrived(
                request.getUserLat(), request.getUserLon(),
                place.getLatitude(), place.getLongitude()
        );

        if (!gpsVerified) {
            throw new CustomException(ErrorCode.NOT_IN_RANGE);
        }

        // 코스 연결 (있는 경우)
        Course course = null;
        if (request.getCourseId() != null) {
            course = courseRepository.findById(request.getCourseId())
                    .orElseThrow(() -> new CustomException(ErrorCode.COURSE_NOT_FOUND));
        }

        // 방문 저장
        visitRepository.save(Visit.builder()
                .user(user)
                .place(place)
                .course(course)
                .gpsVerified(true)
                .build());

        // 스탬프 적립
        stampService.earnStamp(user);

        return place.getName();  // "OOO님, OOO 방문 완료!" 팝업용 장소명 반환
    }
}