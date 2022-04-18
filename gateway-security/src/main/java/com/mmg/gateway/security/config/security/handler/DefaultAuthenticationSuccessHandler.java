package com.mmg.gateway.security.config.security.handler;

import com.alibaba.fastjson.JSONObject;
import com.mmg.commons.config.module.ApiResult;
import com.mmg.commons.config.utils.CommonMethods;
import com.mmg.commons.config.utils.ConstantUtil;
import com.mmg.gateway.security.config.security.bean.CustomUserDetail;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.core.io.buffer.DataBuffer;
import org.springframework.core.io.buffer.DataBufferFactory;
import org.springframework.data.redis.core.RedisTemplate;
import org.springframework.http.MediaType;
import org.springframework.security.core.Authentication;
import org.springframework.security.web.server.WebFilterExchange;
import org.springframework.security.web.server.authentication.ServerAuthenticationSuccessHandler;
import org.springframework.stereotype.Component;
import reactor.core.publisher.Mono;

import java.util.HashMap;
import java.util.Map;
import java.util.concurrent.TimeUnit;

/**
 * @Auther: fan
 * @Date: 2022/3/29
 * @Description: 登录成功处理
 */
@Component
public class DefaultAuthenticationSuccessHandler implements ServerAuthenticationSuccessHandler {

    @Autowired
    private RedisTemplate<String, Object> redisTemplate;

    @Override
    public Mono<Void> onAuthenticationSuccess(WebFilterExchange webFilterExchange, Authentication authentication) {
        return Mono.defer(() -> Mono.just(webFilterExchange.getExchange().getResponse()).flatMap(response -> {
            DataBufferFactory dataBufferFactory = response.bufferFactory();
            CustomUserDetail userDetail = (CustomUserDetail) authentication.getDetails();
            Map<String, Object> tokenMap = new HashMap<>(2);
            String token = CommonMethods.uuid();
            tokenMap.put("token", token);
            tokenMap.put("username", userDetail.getUsername());
            redisTemplate.opsForValue().set(ConstantUtil.TOKEN.concat(token), userDetail, 2, TimeUnit.HOURS);
            redisTemplate.opsForValue().set(ConstantUtil.TOKEN_INFO.concat(token), JSONObject.parse(JSONObject.toJSONString(userDetail)), 2, TimeUnit.HOURS);
            DataBuffer dataBuffer = dataBufferFactory.wrap(JSONObject.toJSONString(ApiResult.success(tokenMap)).getBytes());
            response.getHeaders().setContentType(MediaType.APPLICATION_JSON);
            return response.writeWith(Mono.just(dataBuffer));
        }));
    }
}
