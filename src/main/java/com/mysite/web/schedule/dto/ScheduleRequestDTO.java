package com.mysite.web.schedule.dto;

import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import lombok.ToString;

@Getter
@Setter
@NoArgsConstructor
@ToString
public class ScheduleRequestDTO {
	private String title;
	private String date;
    private String start;
    private String end;
    private boolean allDay;
    private String type;
    private String description;
    private String status;
}
