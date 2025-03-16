package com.mysite.web.schedule.dto;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.ToString;

@Getter
@Builder
@NoArgsConstructor
@AllArgsConstructor
@ToString
public class HabitStatusDTO {
    private Long habitId;
    private String name;
    private String description;
    private String frequency;
    private Integer goalCount;
    private boolean isCompleted;
    private String logDate;
}