package com.mysite.web.schedule.model;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;
import lombok.ToString;

import java.time.LocalDate;

@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
@ToString
public class HabitLogEntity {
    private Long logId;
    private Long habitId;
    private Long userId;
    private LocalDate logDate;
    private String status;
    private String createdAt;
    private String updatedAt;
}