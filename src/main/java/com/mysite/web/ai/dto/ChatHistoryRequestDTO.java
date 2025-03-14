package com.mysite.web.ai.dto;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class ChatHistoryRequestDTO {
    private Long userId;   // JWT에서 추출한 사용자 ID
    private Long chatId;   // 컨트롤러에서 받아온 chatId
}
