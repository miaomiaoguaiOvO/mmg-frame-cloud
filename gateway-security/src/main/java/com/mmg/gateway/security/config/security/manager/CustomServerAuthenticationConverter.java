package com.mmg.gateway.security.config.security.manager;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.mmg.gateway.security.config.security.bean.LoginRequest;
import org.springframework.http.codec.DecoderHttpMessageReader;
import org.springframework.http.codec.HttpMessageReader;
import org.springframework.http.codec.json.Jackson2JsonDecoder;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.web.server.authentication.ServerAuthenticationConverter;
import org.springframework.stereotype.Component;
import org.springframework.web.reactive.function.server.ServerRequest;
import org.springframework.web.server.ServerWebExchange;
import reactor.core.publisher.Mono;

import java.util.Collections;

/**
 * @Auther: fan
 * @Date: 2022/4/14
 * @Description: 登录请求转换器 form-data -> application/json
 */
@Component
public class CustomServerAuthenticationConverter implements ServerAuthenticationConverter {

    @Override
    public Mono<Authentication> convert(ServerWebExchange serverWebExchange) {
        ObjectMapper mapper = new ObjectMapper();
        HttpMessageReader<Object> messageReader = new DecoderHttpMessageReader<>(new Jackson2JsonDecoder(mapper));
        ServerRequest serverRequest = ServerRequest.create(serverWebExchange, Collections.singletonList(messageReader));
        return serverRequest.bodyToMono(LoginRequest.class).map(m -> {
            UsernamePasswordAuthenticationToken authenticationToken = new UsernamePasswordAuthenticationToken(m.getUsername(), m.getPassword());
            SecurityContextHolder.getContext().setAuthentication(authenticationToken);
            return authenticationToken;
        });
    }
}
