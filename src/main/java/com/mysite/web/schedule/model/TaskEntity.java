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
public class TaskEntity {
    private Long taskId;
    private Long userId;
    private String content;
    private Integer priority;
    private Boolean isCompleted;
    private String checkStatus;
    private String createdAt;     // timestamp without time zone 타입과 매칭
    private String updatedAt;     // timestamp without time zone 타입과 매칭
}