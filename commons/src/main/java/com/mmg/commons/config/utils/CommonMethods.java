package com.mmg.commons.config.utils;

import java.util.UUID;

/**
 * @Auther: fan
 * @Date: 2022/3/29
 * @Description:
 */
public class CommonMethods {

    public static String uuid() {
        return UUID.randomUUID().toString().replaceAll("-", "");
    }
}
