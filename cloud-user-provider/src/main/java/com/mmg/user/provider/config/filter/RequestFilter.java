package com.mmg.user.provider.config.filter;

import com.alibaba.fastjson.JSONObject;
import com.mmg.commons.config.utils.ConstantUtil;
import com.mmg.user.provider.config.LocalUserHolder;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.redis.core.RedisTemplate;
import org.springframework.stereotype.Component;
import org.springframework.web.filter.OncePerRequestFilter;

import javax.servlet.FilterChain;
import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;

/**
 * @Auther: Fan
 * @Date: 2022/4/16
 * @Description:
 */
@Component
public class RequestFilter extends OncePerRequestFilter {

    @Autowired
    private RedisTemplate<String, Object> redisTemplate;

    @Override
    protected void doFilterInternal(HttpServletRequest request, HttpServletResponse response, FilterChain filterChain) throws ServletException, IOException {
        String token = request.getHeader(ConstantUtil.AUTHORIZE);
        if (token != null) {
            String tokenInfo = ConstantUtil.TOKEN_INFO + token.substring(ConstantUtil.AUTHORIZE_CODE.length());
            JSONObject jsonObject = (JSONObject) redisTemplate.opsForValue().get(tokenInfo);
            if (jsonObject != null) {
                LocalUserHolder.setUserId(jsonObject.getString("username"));
            }
        }
        filterChain.doFilter(request, response);
    }
}
