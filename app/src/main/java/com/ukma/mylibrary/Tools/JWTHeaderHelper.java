package com.ukma.mylibrary.Tools;

import com.ukma.mylibrary.managers.AuthManager;

import java.util.HashMap;
import java.util.Map;

public class JWTHeaderHelper {
    public static Map<String, String> createHeader() {
        String token = AuthManager.JWT_TOKEN;
        Map<String, String> params = new HashMap<>();
        if (token != null && !token.isEmpty()) {
            params.put("Authorization", "Bearer " + token);
        }
        return params;
    }
}
