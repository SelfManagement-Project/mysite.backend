package com.mysite.web.ai.service.impl;

import java.util.Collections;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.mysite.web.ai.dto.ChatHistoryResponse;
import com.mysite.web.ai.mapper.ChatHistoryMapper;
import com.mysite.web.ai.service.ChatHistoryService;
import com.mysite.web.login.util.JwtUtil;

import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;

@Slf4j
@Service
@RequiredArgsConstructor
public class ChatHistoryServiceImpl implements ChatHistoryService {

    @Autowired
    private ChatHistoryMapper chatHistoryMapper;

    @Override
    public List<ChatHistoryResponse> getChatHistories(String token, String search) {
        try {
            // Bearer 토큰에서 실제 토큰 값 추출
            String jwtToken = token.replace("Bearer ", "").trim();

            // JWT 토큰 검증 및 사용자 정보 추출
            Long userId = JwtUtil.getUserIdFromToken(jwtToken);
            if (userId == null) {
                throw new RuntimeException("유효하지 않은 토큰입니다.");
            }

            // 사용자의 채팅 히스토리 목록 조회
            List<ChatHistoryResponse> chatHistoryList = chatHistoryMapper.getChatHistoriesByUserId(userId, search);
            System.out.println("chatHistoryList::::" + chatHistoryList);
            if (chatHistoryList == null) {
                log.warn("chatHistoryList is null, returning empty list.");
                return Collections.emptyList();
            }
            return chatHistoryList;


        } catch (Exception e) {
            log.error("채팅 기록 조회 중 오류 발생: {}", e.getMessage(), e);
            throw new RuntimeException("채팅 기록 조회 중 오류가 발생했습니다.", e);
        }
    }
}
