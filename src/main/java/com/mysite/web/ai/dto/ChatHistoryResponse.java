package com.mysite.web.ai.dto;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class ChatHistoryResponse {
    private Long historyId;
    private Long chatId;
    private String sessionId;
    private String messageType;
    private String content;
    private String createdAt;
}
