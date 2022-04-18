package com.mmg.gateway.security.config.security.handler;

import com.alibaba.fastjson.JSONObject;
import com.mmg.commons.config.exception.ApiErrorCode;
import com.mmg.commons.config.module.ApiResult;
import org.springframework.core.io.buffer.DataBuffer;
import org.springframework.core.io.buffer.DataBufferFactory;
import org.springframework.http.HttpHeaders;
import org.springframework.http.MediaType;
import org.springframework.security.authentication.*;
import org.springframework.security.core.AuthenticationException;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.security.web.server.WebFilterExchange;
import org.springframework.security.web.server.authentication.ServerAuthenticationFailureHandler;
import org.springframework.stereotype.Component;
import reactor.core.publisher.Mono;

import java.util.Map;

/**
 * @Auther: fan
 * @Date: 2022/3/29
 * @Description: 登录失败处理
 */
@Component
public class DefaultAuthenticationFailureHandler implements ServerAuthenticationFailureHandler {
    @Override
    public Mono<Void> onAuthenticationFailure(WebFilterExchange webFilterExchange, AuthenticationException exception) {
        return Mono.defer(() -> Mono.just(webFilterExchange.getExchange()
                .getResponse()).flatMap(response -> {
            DataBufferFactory dataBufferFactory = response.bufferFactory();
            ApiResult<Map<String, Object>> resultVO = new ApiResult<>();
            // 账号不存在
            if (exception instanceof UsernameNotFoundException) {
                resultVO = ApiResult.failed(ApiErrorCode.ACCOUNT_NOT_EXIST);
                // 用户名或密码错误
            } else if (exception instanceof BadCredentialsException) {
                resultVO = ApiResult.failed(ApiErrorCode.LOGIN_PASSWORD_ERROR);
                // 账号已过期
            } else if (exception instanceof AccountExpiredException) {
                resultVO = ApiResult.failed(ApiErrorCode.ACCOUNT_EXPIRED);
                // 账号已被锁定
            } else if (exception instanceof LockedException) {
                resultVO = ApiResult.failed(ApiErrorCode.ACCOUNT_LOCKED);
                // 用户凭证已失效
            } else if (exception instanceof CredentialsExpiredException) {
                resultVO = ApiResult.failed(ApiErrorCode.ACCOUNT_CREDENTIAL_EXPIRED);
                // 账号已被禁用
            } else if (exception instanceof DisabledException) {
                resultVO = ApiResult.failed(ApiErrorCode.ACCOUNT_DISABLE);
            }
            DataBuffer dataBuffer = dataBufferFactory.wrap(JSONObject.toJSONString(resultVO).getBytes());
            response.getHeaders().setContentType(MediaType.APPLICATION_JSON);
            return response.writeWith(Mono.just(dataBuffer));
        }));
    }
}
