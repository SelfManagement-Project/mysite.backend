package com.mysite.web.health.dto;

import lombok.*;

@Getter
@Builder
@NoArgsConstructor
@AllArgsConstructor
@ToString
public class DietResponseDTO {
    private Long dietId;
    private Long userId;
    private String mealType;
    private int calories;
    private int protein;
    private int carbs;
    private String createdAt;
    private String updatedAt;
}
