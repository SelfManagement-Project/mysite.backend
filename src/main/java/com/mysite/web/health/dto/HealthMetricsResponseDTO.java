package com.mysite.web.health.dto;

import lombok.*;

@Getter
@Builder
@NoArgsConstructor
@AllArgsConstructor
@ToString
public class HealthMetricsResponseDTO {
    private Long metricId;
    private Long userId;
    private double weight;
    private double targetWeight;
    private double bmi;
    private String createdAt;
    private String updatedAt;
}
