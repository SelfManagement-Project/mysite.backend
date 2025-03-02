package com.mysite.web.health.model;

import lombok.*;
import java.time.LocalDateTime;

@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
@ToString
public class DietEntity {

    private Long dietId;

    private Long userId;

    private String mealType;

    private int calories;

    private int protein;

    private int carbs;

    private LocalDateTime createdAt;
    private LocalDateTime updatedAt;
}
