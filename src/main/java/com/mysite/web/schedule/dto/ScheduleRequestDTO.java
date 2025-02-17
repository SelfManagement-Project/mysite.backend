package com.mysite.web.schedule.dto;

import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Getter
@Setter
@NoArgsConstructor
public class ScheduleRequestDTO {
	private String title;
    private String start;
    private String end;
    private boolean allDay;
    private String description;
    private String status;
}
