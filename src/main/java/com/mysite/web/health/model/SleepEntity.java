package com.mysite.web.health.model;

import lombok.*;
import java.time.LocalDateTime;

@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
@ToString
public class SleepEntity {

    private Long sleepId;

    private Long userId;

    private String sleepStart;

    private String sleepEnd;

    private int sleepQuality;

    private LocalDateTime createdAt;
    private LocalDateTime updatedAt;
}
