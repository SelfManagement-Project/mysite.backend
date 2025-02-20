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
public class CalendarResponseDTO {
    private Long scheduleId;      // 일정 ID
    private Long userId;          // 일정 생성자 ID
    private String email;      // 일정 생성자 이름
    private String userName;      // 일정 생성자 이름
    private String title;
    private String date;
    private String start;
    private String end;
    private String type;
    private boolean isCompleted;
    private String description;
    private String status;
    private String createdAt;
    private String updatedAt;
}
