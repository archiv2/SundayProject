// dto/course/AddPlaceToCourseRequest.java
package com.sunday.server.dto.course;

import lombok.Getter;
import lombok.NoArgsConstructor;

@Getter
@NoArgsConstructor
public class AddPlaceToCourseRequest {

    private Long placeId;
    private int slotOrder;   // 1, 2, 3 중 하나
}