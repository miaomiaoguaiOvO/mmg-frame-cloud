package com.mmg.gateway.security.config.security.manager;

import com.mmg.commons.config.utils.ConstantUtil;
import com.mmg.gateway.security.config.security.bean.CustomUserDetail;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.redis.core.RedisTemplate;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.context.SecurityContext;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.core.context.SecurityContextImpl;
import org.springframework.security.web.server.context.ServerSecurityContextRepository;
import org.springframework.stereotype.Component;
import org.springframework.web.server.ServerWebExchange;
import reactor.core.publisher.Mono;

import java.util.concurrent.TimeUnit;

/**
 * @Auther: fan
 * @Date: 2022/4/15
 * @Description:
 */
@Component
public class CustomSecurityContextRepository implements ServerSecurityContextRepository {

    @Autowired
    private RedisTemplate<String, Object> redisTemplate;

    @Override
    public Mono<Void> save(ServerWebExchange exchange, SecurityContext context) {
        return Mono.empty();
    }

    @Override
    public Mono<SecurityContext> load(ServerWebExchange exchange) {
        String token = exchange.getRequest().getHeaders().getFirst(ConstantUtil.AUTHORIZE);
        if (token != null) {
            token = token.substring(ConstantUtil.AUTHORIZE_CODE.length());
            String tokenKey = ConstantUtil.TOKEN + token;
            String tokenInfo = ConstantUtil.TOKEN_INFO + token;
            CustomUserDetail userDetail = (CustomUserDetail) redisTemplate.opsForValue().get(tokenKey);
            //token续期2小时
            if (userDetail != null) {
                redisTemplate.expire(tokenKey, 2, TimeUnit.HOURS);
                redisTemplate.expire(tokenInfo, 2, TimeUnit.HOURS);
            }
            // 将用户信息存入 authentication，方便后续校验
            UsernamePasswordAuthenticationToken authentication = new UsernamePasswordAuthenticationToken(userDetail, null, userDetail == null ? null : userDetail.getAuthorities());
            authentication.setDetails(userDetail);
            // 将 authentication 存入 ThreadLocal，方便后续获取用户信息
            SecurityContextHolder.getContext().setAuthentication(authentication);
            return Mono.just(authentication).map(SecurityContextImpl::new);
        }
        return Mono.empty();
    }
}
