// dto/course/CreateCourseRequest.java
package com.sunday.server.dto.course;

import lombok.Getter;
import lombok.NoArgsConstructor;
import java.time.LocalDate;

@Getter
@NoArgsConstructor
public class CreateCourseRequest {

    private Long userId;
    private String mood;
    private LocalDate date;
}