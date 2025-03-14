package com.mysite.web.ai.dto;

import com.mysite.web.ai.model.ChatHistoryEntity;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import lombok.ToString;

@Getter
@Setter
@Builder
@NoArgsConstructor
@AllArgsConstructor
@ToString
public class ChatHistoryResponseDTO {
    private Long historyId;
    private Long chatId;
    private String sessionId;
    private String messageType;
    private String content;
    private String createdAt;

    // 👇 이 메소드를 직접 추가해주세요.
    public static ChatHistoryResponseDTO fromEntity(ChatHistoryEntity entity) {
        return ChatHistoryResponseDTO.builder()
            .historyId(entity.getHistoryId())
            .chatId(entity.getChatId())
            .sessionId(entity.getSessionId())
            .messageType(entity.getMessageType())
            .content(entity.getContent())
            .createdAt(entity.getCreatedAt())
            .build();
    }
}
