package com.mysite.web.schedule.dto;

import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import lombok.ToString;

@Getter
@Setter
@NoArgsConstructor
@ToString
public class HabitRequestDTO {
    private Long habitId;
    private String name;
    private String description;
    private String frequency;
}