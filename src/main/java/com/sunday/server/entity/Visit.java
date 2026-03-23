// entity/Visit.java
package com.sunday.server.entity;

import jakarta.persistence.*;
import lombok.*;
import java.time.LocalDateTime;

@Entity
@Table(name = "visits")
@Getter
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class Visit {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "user_id", nullable = false)
    private User user;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "place_id", nullable = false)
    private Place place;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "course_id")
    private Course course;        // 코스 통해서 방문한 경우

    @Column(nullable = false)
    private boolean gpsVerified;  // GPS 인증 여부

    @Column(nullable = false, updatable = false)
    private LocalDateTime arrivedAt;

    @PrePersist
    protected void onCreate() {
        this.arrivedAt = LocalDateTime.now();
    }
}