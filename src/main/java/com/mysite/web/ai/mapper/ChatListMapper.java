package com.mysite.web.ai.mapper;

import java.util.List;
import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Param;

import com.mysite.web.ai.dto.ChatHistoryRequestDTO;
import com.mysite.web.ai.dto.ChatListResponse;
import com.mysite.web.ai.model.ChatHistoryEntity;

@Mapper
public interface ChatListMapper {
    List<ChatListResponse> getChatListByUserId(
        @Param("userId") Long userId,
        @Param("search") String search
    );
    List<ChatListResponse> getChatListRecentByUserId(Long userId);
    List<ChatHistoryEntity> getChatHistoryByChatId(ChatHistoryRequestDTO requestDTO);
    
}
