package com.mysite.web.health.model;

import lombok.*;
import java.time.LocalDateTime;

@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
@ToString
public class ExerciseEntity {

    private Long exerciseId;
    private Long userId;
    private Long facilityId;  // 운동 시설 ID (optional)
    private String exerciseType;
    private int duration;  // 운동 시간 (분)
    private int caloriesBurned; // 소모 칼로리
    private String createdAt;
    private String updatedAt;
}
