package com.mmg.gateway.security.config.security.bean;

import lombok.Data;

/**
 * @Auther: fan
 * @Date: 2022/3/31
 * @Description:
 */

@Data
public class LoginRequest {
    private String username;
    private String password;
}
