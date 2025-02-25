package com.mysite.web.schedule.dto;

import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import lombok.ToString;

@Getter
@Setter
@NoArgsConstructor
@ToString
public class CalendarRequestDTO {
	private Long scheduleId; 
	private String title;
	private String date;
    private String start;
    private String end;
    private boolean isCompleted;
    private boolean allDay;
    private String type;
    private String description;
    private String status;
    private Integer priority;
}
