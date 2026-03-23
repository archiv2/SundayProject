// entity/CoursePlace.java
package com.sunday.server.entity;

import jakarta.persistence.*;
import lombok.*;

@Entity
@Table(name = "course_places")
@Getter
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class CoursePlace {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "course_id", nullable = false)
    private Course course;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "place_id", nullable = false)
    private Place place;

    @Column(nullable = false)
    private int slotOrder;        // 슬롯 순서 (1, 2, 3)
}