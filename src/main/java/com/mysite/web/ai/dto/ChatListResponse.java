package com.mysite.web.ai.dto;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class ChatListResponse {
	private Long chatId;       // 채팅 ID
    private Long userId;       // 사용자 ID
    private String sessionId;  // 세션 ID
    private String message;    // 사용자가 입력한 메시지
    private String response;   // 챗봇 응답
    private String createdAt;  // 생성일시
    private Boolean isCompleted; // 완료 여부
}
