package com.mysite.web.schedule.dto;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class TaskRequestDTO {
    private String content;
    private Integer priority;
    private Boolean isCompleted;
    private String checkStatus;
}