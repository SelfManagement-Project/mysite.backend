package com.mysite.web.schedule.model;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;
import lombok.ToString;

@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
@ToString
public class HabitEntity {
    private Long habitId;
    private Long userId;
    private String name;
    private String description;
    private String frequency;
    private Integer goalCount;
    private String createdAt;
    private String updatedAt;
}