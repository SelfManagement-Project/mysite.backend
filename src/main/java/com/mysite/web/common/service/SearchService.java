package com.mysite.web.common.service;

import java.util.List;
import java.util.Map;

public interface SearchService {
    List<Map<String, Object>> searchAllTables(String keyword);
}
