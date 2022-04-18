package com.mmg.user.provider.config;

import org.springframework.stereotype.Component;

/**
 * @Auther: Fan
 * @Date: 2022/4/16
 * @Description: 当前用户存储类
 */
@Component
public class LocalUserHolder {
    private static final ThreadLocal<String> userId = new ThreadLocal<>();

    public static String getUserId() {
        return userId.get();
    }

    public static void setUserId(String userId) {
        LocalUserHolder.userId.set(userId);
    }
}
