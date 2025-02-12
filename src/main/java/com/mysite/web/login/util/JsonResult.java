package com.mysite.web.login.util;

import lombok.Getter;
import lombok.AccessLevel;
import lombok.AllArgsConstructor;

@Getter
@AllArgsConstructor(access = AccessLevel.PRIVATE)
public class JsonResult {

    private final String result;
    private final Object apiData;
    private final String message;

    public static JsonResult success(Object data) {
        return new JsonResult("success", data, null);
    }

    public static JsonResult fail(String message) {
        return new JsonResult("fail", null, message);
    }
}