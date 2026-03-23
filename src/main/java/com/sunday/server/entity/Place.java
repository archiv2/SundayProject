// entity/Place.java
package com.sunday.server.entity;

import jakarta.persistence.*;
import lombok.*;

@Entity
@Table(name = "places")
@Getter
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class Place {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(nullable = false)
    private String name;

    @Column(nullable = false)
    private String mood;          // "담백한" | "감각적인" | "활기찬" | "일요일픽"

    private String imageUrl;

    private String tags;          // "#조용한 #깨끗한 #공기좋은" (쉼표 구분 저장)

    private String recommendTarget; // "이런 분들께 강추" 설명 텍스트

    @Column(nullable = false)
    private Double latitude;      // 위도

    @Column(nullable = false)
    private Double longitude;     // 경도

    private Integer healingScore; // 힐링지수 (1~100)

    private String address;
}