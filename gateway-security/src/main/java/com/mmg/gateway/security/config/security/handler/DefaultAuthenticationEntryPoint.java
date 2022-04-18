package com.mmg.gateway.security.config.security.handler;

import com.alibaba.fastjson.JSONObject;
import com.mmg.commons.config.exception.ApiErrorCode;
import com.mmg.commons.config.module.ApiResult;
import org.springframework.core.io.buffer.DataBuffer;
import org.springframework.core.io.buffer.DataBufferFactory;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.security.core.AuthenticationException;
import org.springframework.security.web.server.ServerAuthenticationEntryPoint;
import org.springframework.stereotype.Component;
import org.springframework.web.server.ServerWebExchange;
import reactor.core.publisher.Mono;

import java.nio.charset.Charset;

/**
 * @Auther: fan
 * @Date: 2022/3/29
 * @Description: 未认证处理
 */
@Component
public class DefaultAuthenticationEntryPoint implements ServerAuthenticationEntryPoint {

    @Override
    public Mono<Void> commence(ServerWebExchange exchange, AuthenticationException ex) {
        return Mono.defer(() -> Mono.just(exchange.getResponse())).flatMap(response -> {
            response.setStatusCode(HttpStatus.UNAUTHORIZED);
            response.getHeaders().setContentType(MediaType.APPLICATION_JSON);
            DataBufferFactory dataBufferFactory = response.bufferFactory();
            String result = JSONObject.toJSONString(ApiResult.failed(ApiErrorCode.UNAUTHORIZED));
            DataBuffer buffer = dataBufferFactory.wrap(result.getBytes(
                    Charset.defaultCharset()));
            return response.writeWith(Mono.just(buffer));
        });
    }
}
