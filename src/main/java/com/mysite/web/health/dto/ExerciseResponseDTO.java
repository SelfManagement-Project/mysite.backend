package com.mysite.web.health.dto;

import lombok.*;

@Getter
@Builder
@NoArgsConstructor
@AllArgsConstructor
@ToString
public class ExerciseResponseDTO {
    private Long exerciseId;
    private Long userId;
    private Long facilityId;
    private String exerciseType;
    private int duration;
    private int caloriesBurned;
    private String createdAt;
    private String updatedAt;
}
