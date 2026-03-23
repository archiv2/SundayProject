// service/MypageService.java
package com.sunday.server.service;

import com.sunday.server.dto.mypage.MypageRecordResponse;
import com.sunday.server.dto.mypage.MyPageSummaryResponse;
import com.sunday.server.entity.Review;
import com.sunday.server.entity.User;
import com.sunday.server.entity.Visit;
import com.sunday.server.exception.CustomException;
import com.sunday.server.exception.ErrorCode;
import com.sunday.server.repository.*;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.time.LocalDateTime;
import java.util.*;
import java.util.stream.Collectors;

@Service
@RequiredArgsConstructor
public class MypageService {

    private final UserRepository userRepository;
    private final VisitRepository visitRepository;
    private final ReviewRepository reviewRepository;
    private final StampRepository stampRepository;

    /**
     * 마이페이지 요약 (기간 필터 적용)
     * period: "weekly" | "monthly" | "total"
     */
    @Transactional(readOnly = true)
    public MyPageSummaryResponse getSummary(Long userId, String period) {
        User user = userRepository.findById(userId)
                .orElseThrow(() -> new CustomException(ErrorCode.USER_NOT_FOUND));

        LocalDateTime[] range = getDateRange(period);
        LocalDateTime from = range[0];
        LocalDateTime to = range[1];

        // 기간별 방문 목록
        List<Visit> visits = visitRepository
                .findByUserIdAndArrivedAtBetweenOrderByArrivedAtDesc(userId, from, to);

        // 스탬프 개수
        int stampCount = stampRepository
                .findByUserIdAndEarnedAtBetween(userId, from, to).size();

        // 무드 비율 계산
        Map<String, Integer> moodRatio = calculateMoodRatio(userId, visits);

        // 대표 무드 (가장 높은 비율)
        String representMood = moodRatio.entrySet().stream()
                .max(Map.Entry.comparingByValue())
                .map(Map.Entry::getKey)
                .orElse("없음");

        return MyPageSummaryResponse.builder()
                .nickname(user.getNickname())
                .profileImageUrl(user.getProfileImageUrl())
                .visitCount(visits.size())
                .stampCount(stampCount)
                .representMood(representMood)
                .moodRatio(moodRatio)
                .build();
    }

    /**
     * 마이페이지 기록 상세 (방문 장소 + 리뷰)
     */
    @Transactional(readOnly = true)
    public MypageRecordResponse getRecords(Long userId, String period) {
        LocalDateTime[] range = getDateRange(period);
        LocalDateTime from = range[0];
        LocalDateTime to = range[1];

        List<Visit> visits = visitRepository
                .findByUserIdAndArrivedAtBetweenOrderByArrivedAtDesc(userId, from, to);

        // 유저 리뷰 맵 (placeId → Review)
        Map<Long, Review> reviewMap = reviewRepository
                .findByUserIdOrderByCreatedAtDesc(userId)
                .stream()
                .collect(Collectors.toMap(
                        r -> r.getPlace().getId(),
                        r -> r,
                        (r1, r2) -> r1   // 중복 시 최신 유지
                ));

        List<MypageRecordResponse.VisitRecord> records = visits.stream()
                .map(visit -> {
                    Review review = reviewMap.get(visit.getPlace().getId());
                    return MypageRecordResponse.VisitRecord.builder()
                            .placeId(visit.getPlace().getId())
                            .placeName(visit.getPlace().getName())
                            .imageUrl(visit.getPlace().getImageUrl())
                            .mood(review != null ? review.getMood() : null)
                            .comment(review != null ? review.getComment() : null)
                            .arrivedAt(visit.getArrivedAt().toString())
                            .build();
                })
                .collect(Collectors.toList());

        // 스탬프 개수
        int stampCount = stampRepository
                .findByUserIdAndEarnedAtBetween(userId, from, to).size();

        // 대표 무드
        Map<String, Integer> moodRatio = calculateMoodRatio(userId, visits);
        String representMood = moodRatio.entrySet().stream()
                .max(Map.Entry.comparingByValue())
                .map(Map.Entry::getKey)
                .orElse("없음");

        return MypageRecordResponse.builder()
                .visitCount(visits.size())
                .stampCount(stampCount)
                .representMood(representMood)
                .records(records)
                .build();
    }

    // --- private helpers ---

    private LocalDateTime[] getDateRange(String period) {
        LocalDateTime to = LocalDateTime.now();
        LocalDateTime from;

        switch (period) {
            case "weekly":
                from = to.minusWeeks(1);
                break;
            case "monthly":
                from = to.minusMonths(1);
                break;
            default: // "total"
                from = LocalDateTime.of(2000, 1, 1, 0, 0);
                break;
        }
        return new LocalDateTime[]{from, to};
    }

    private Map<String, Integer> calculateMoodRatio(Long userId, List<Visit> visits) {
        if (visits.isEmpty()) return Collections.emptyMap();

        // 방문한 장소의 무드 집계
        Map<String, Long> moodCount = visits.stream()
                .collect(Collectors.groupingBy(
                        v -> v.getPlace().getMood(),
                        Collectors.counting()
                ));

        long total = visits.size();

        // 비율(%) 계산
        Map<String, Integer> moodRatio = new LinkedHashMap<>();
        moodCount.forEach((mood, count) ->
                moodRatio.put(mood, (int) Math.round(count * 100.0 / total)));

        return moodRatio;
    }
}