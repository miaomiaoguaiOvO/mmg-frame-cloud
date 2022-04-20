package com.mmg.gateway.security.config.security.bean;

import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;

/**
 * @Auther: fan
 * @Date: 2022/4/20
 * @Description:
 */
public class CustomUsernamePasswordAuthenticationToken extends UsernamePasswordAuthenticationToken {

    private String code;

    public CustomUsernamePasswordAuthenticationToken(Object principal, Object credentials, String code) {
        super(principal, credentials);
        this.code = code;
    }

    public String getCode() {
        return code;
    }

    public void setCode(String code) {
        this.code = code;
    }
}
