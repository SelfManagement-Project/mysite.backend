package com.mysite.web.schedule.dto;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class HabitProgressDTO {
    private Long habitId;
    private String name;
    private int completed;  // 진행 퍼센트
    private int remaining;  // 미진행 퍼센트
}
