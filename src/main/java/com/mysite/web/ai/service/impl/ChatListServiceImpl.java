package com.mysite.web.ai.service.impl;

import java.util.Collections;
import java.util.List;
import java.util.stream.Collectors;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.mysite.web.ai.dto.ChatHistoryRequestDTO;
import com.mysite.web.ai.dto.ChatHistoryResponseDTO;
import com.mysite.web.ai.dto.ChatListResponse;
import com.mysite.web.ai.mapper.ChatListMapper;
import com.mysite.web.ai.model.ChatHistoryEntity;
import com.mysite.web.ai.service.ChatListService;
import com.mysite.web.login.util.JwtUtil;

import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;

@Slf4j
@Service
@RequiredArgsConstructor
public class ChatListServiceImpl implements ChatListService {

    @Autowired
    private ChatListMapper chatListMapper;

    @Override
    public List<ChatListResponse> getChatList(String token, String search) {
        try {
            // Bearer 토큰에서 실제 토큰 값 추출
            String jwtToken = token.replace("Bearer ", "").trim();

            // JWT 토큰 검증 및 사용자 정보 추출
            Long userId = JwtUtil.getUserIdFromToken(jwtToken);
            if (userId == null) {
                throw new RuntimeException("유효하지 않은 토큰입니다.");
            }

            // 사용자의 채팅 히스토리 목록 조회
            List<ChatListResponse> chatList = chatListMapper.getChatListByUserId(userId, search);
//            System.out.println("chatList::::" + chatList);
            if (chatList == null) {
                log.warn("chatHistoryList is null, returning empty list.");
                return Collections.emptyList();
            }
            return chatList;


        } catch (Exception e) {
            log.error("채팅 기록 조회 중 오류 발생: {}", e.getMessage(), e);
            throw new RuntimeException("채팅 기록 조회 중 오류가 발생했습니다.", e);
        }
    }
    
    @Override
    public List<ChatListResponse> getChatListRecent(String token) {
        try {
            // Bearer 토큰에서 실제 토큰 값 추출
            String jwtToken = token.replace("Bearer ", "").trim();

            // JWT 토큰 검증 및 사용자 정보 추출
            Long userId = JwtUtil.getUserIdFromToken(jwtToken);
            if (userId == null) {
                throw new RuntimeException("유효하지 않은 토큰입니다.");
            }

            // 사용자의 채팅 히스토리 목록 조회
            List<ChatListResponse> chatList = chatListMapper.getChatListRecentByUserId(userId);
//            System.out.println("chatList::::" + chatList);
            if (chatList == null) {
                log.warn("chatList is null, returning empty list.");
                return Collections.emptyList();
            }
            return chatList;


        } catch (Exception e) {
            log.error("채팅 기록 조회 중 오류 발생: {}", e.getMessage(), e);
            throw new RuntimeException("채팅 기록 조회 중 오류가 발생했습니다.", e);
        }
    }
    
    @Override
    public List<ChatHistoryResponseDTO> getChatHistory(String token, Long chatId) {
        try {
            String jwtToken = token.replace("Bearer ", "").trim();
            Long userId = JwtUtil.getUserIdFromToken(jwtToken);
            if (userId == null) {
                throw new RuntimeException("유효하지 않은 토큰입니다.");
            }

            // RequestDTO로 변환 (userId + chatId 함께 관리)
            ChatHistoryRequestDTO requestDTO = ChatHistoryRequestDTO.builder()
                    .userId(userId)
                    .chatId(chatId)
                    .build();

            List<ChatHistoryEntity> entities = chatListMapper.getChatHistoryByChatId(requestDTO);

            if (entities == null || entities.isEmpty()) {
                log.warn("chatHistory is empty for chatId: {}", chatId);
                return Collections.emptyList();
            }

            // Entity → ResponseDTO 변환
            return entities.stream()
                           .map(ChatHistoryResponseDTO::fromEntity)
                           .collect(Collectors.toList());

        } catch (Exception e) {
            log.error("채팅 히스토리 조회 중 오류 발생: {}", e.getMessage(), e);
            throw new RuntimeException("채팅 히스토리 조회 중 오류가 발생했습니다.", e);
        }
    }

}
