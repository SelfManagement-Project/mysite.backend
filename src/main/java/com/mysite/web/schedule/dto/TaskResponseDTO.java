package com.mysite.web.schedule.dto;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;
import com.mysite.web.schedule.model.TaskEntity;

@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class TaskResponseDTO {
	private Long scheduleId;
	private Long userId; // userId 추가
	private String title;
	private Integer priority;
	private Boolean isCompleted;
//	private String checkStatus;
	private String createdAt;
	private String updatedAt;
}