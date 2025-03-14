package com.mysite.web.ai.mapper;

import java.util.List;
import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Param;
import com.mysite.web.ai.dto.ChatListResponse;

@Mapper
public interface ChatListMapper {
    List<ChatListResponse> getChatListByUserId(
        @Param("userId") Long userId,
        @Param("search") String search
    );
    List<ChatListResponse> getChatListRecentByUserId(Long userId);
    
}
