package com.mysite.web.health.model;

import lombok.*;
import java.time.LocalDateTime;

@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
@ToString
public class HealthMetricsEntity {

    private Long metricId;

    private Long userId;

    private double weight;

    private double targetWeight;

    private double bmi;

    private LocalDateTime createdAt;
    private LocalDateTime updatedAt;
}
