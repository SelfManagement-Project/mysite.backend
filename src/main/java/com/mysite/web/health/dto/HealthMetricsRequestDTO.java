package com.mysite.web.health.dto;

import lombok.*;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@ToString
public class HealthMetricsRequestDTO {
    private Long userId;
    private double weight;
    private double targetWeight;
    private double bmi;
    private double height;
}
