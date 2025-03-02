package com.mysite.web.health.dto;

import lombok.*;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@ToString
public class DietRequestDTO {
    private Long userId;
    private String mealType;
    private int calories;
    private int protein;
    private int carbs;
}
