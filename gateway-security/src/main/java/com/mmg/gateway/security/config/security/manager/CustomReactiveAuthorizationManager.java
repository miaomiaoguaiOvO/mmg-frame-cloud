package com.mmg.gateway.security.config.security.manager;

import com.mmg.commons.config.utils.ConstantUtil;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.redis.core.RedisTemplate;
import org.springframework.http.server.reactive.ServerHttpRequest;
import org.springframework.security.authorization.AuthorizationDecision;
import org.springframework.security.authorization.ReactiveAuthorizationManager;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.web.server.authorization.AuthorizationContext;
import org.springframework.stereotype.Component;
import org.springframework.util.AntPathMatcher;
import reactor.core.publisher.Mono;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;

/**
 * @Auther: fan
 * @Date: 2022/3/30
 * @Description:
 */
@Component
public class CustomReactiveAuthorizationManager implements ReactiveAuthorizationManager<AuthorizationContext> {

    @Autowired
    private RedisTemplate<String, Object> redisTemplate;

    private static final AntPathMatcher antPathMatcher = new AntPathMatcher();

    @Override
    public Mono<AuthorizationDecision> check(Mono<Authentication> authentication, AuthorizationContext object) {
        ServerHttpRequest request = object.getExchange().getRequest();
        //匹配缓存中的路由
        String requestUrl = request.getPath().pathWithinApplication().value();
        Map<Object, Object> entries = redisTemplate.opsForHash().entries(ConstantUtil.CACHE_ROLE_KEY + "_" + requestUrl.split("/")[1]);
        String keyUrl = null;
        for (Object url : entries.keySet()) {
            String urlString = url.toString();
            int i = urlString.indexOf("/");
            String requestMethod = urlString.substring(0, i);
            String realUrl = urlString.substring(requestMethod.length());
            if (antPathMatcher.match(realUrl, requestUrl) && request.getMethodValue().equals(requestMethod)) {
                keyUrl = requestMethod.concat(realUrl);
                break;
            }
        }
        if (keyUrl == null) {
            return Mono.just(new AuthorizationDecision(true));
        }
        Object roles = entries.get(keyUrl);
        List<String> roleList = roles == null ? new ArrayList<>() : (List<String>) roles;
        if (roleList.isEmpty()) {
            return Mono.just(new AuthorizationDecision(false));
        }
        return authentication
                .filter(Authentication::isAuthenticated)
                .flatMapIterable(Authentication::getAuthorities)
                .map(GrantedAuthority::getAuthority)
                .any(c -> roleList.contains(String.valueOf(c)))
                .map(AuthorizationDecision::new)
                .defaultIfEmpty(new AuthorizationDecision(false));
    }

    @Override
    public Mono<Void> verify(Mono<Authentication> authentication, AuthorizationContext object) {
        return Mono.empty();
    }
}
