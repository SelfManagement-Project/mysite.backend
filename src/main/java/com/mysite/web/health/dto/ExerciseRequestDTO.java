package com.mysite.web.health.dto;

import lombok.*;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@ToString
public class ExerciseRequestDTO {
    private Long userId;
    private Long facilityId;
    private String exerciseType;
    private int duration;
    private int caloriesBurned;
}
