package com.mysite.web.ai.mapper;

import java.util.List;
import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Param;
import com.mysite.web.ai.dto.ChatHistoryResponse;

@Mapper
public interface ChatHistoryMapper {
    List<ChatHistoryResponse> getChatHistoriesByUserId(
        @Param("userId") Long userId,
        @Param("search") String search
    );
}
