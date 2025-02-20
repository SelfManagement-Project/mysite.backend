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
public class WeeklyProgressResponseDTO {
	private int completedTasks;  // 완료된 할 일 개수
    private int totalTasks;      // 총 할 일 개수
}
