package com.mysite.web.login.util;

import lombok.experimental.UtilityClass;

@UtilityClass
public class EmailUtils {
    public static boolean isValidEmail(String email) {
        return email.matches("^[A-Za-z0-9+_.-]+@(.+)$");
    }
}