// service/StampService.java
package com.sunday.server.service;

import com.sunday.server.entity.Stamp;
import com.sunday.server.entity.User;
import com.sunday.server.repository.StampRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Service
@RequiredArgsConstructor
public class StampService {

    private final StampRepository stampRepository;

    /**
     * 스탬프 1개 적립
     */
    @Transactional
    public void earnStamp(User user) {
        stampRepository.save(Stamp.builder()
                .user(user)
                .build());
    }

    /**
     * 유저의 전체 스탬프 개수 조회
     */
    @Transactional(readOnly = true)
    public int getStampCount(Long userId) {
        return stampRepository.countByUserId(userId);
    }
}