package com.mmg.gateway.security.config.security.manager;

import com.mmg.commons.config.exception.ApiErrorCode;
import com.mmg.gateway.security.config.security.bean.CustomUserDetail;
import com.mmg.gateway.security.config.security.bean.CustomUserDetailService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.authentication.BadCredentialsException;
import org.springframework.security.authentication.ReactiveAuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Component;
import reactor.core.publisher.Mono;

/**
 * @Auther: fan
 * @Date: 2022/3/30
 * @Description: 自定义登录处理器
 */
@Component
public class CustomReactiveAuthenticationManager implements ReactiveAuthenticationManager {


    @Autowired
    private PasswordEncoder passwordEncoder;
    @Autowired
    private CustomUserDetailService customUserDetailService;
    
    @Override
    public Mono<Authentication> authenticate(Authentication authentication) {

        //用户名
        String username = authentication.getName();
        //密码
        String rawPassword = (String) authentication.getCredentials();
        Mono<UserDetails> userDetailsMono;
        try {
            userDetailsMono = customUserDetailService.findByUsername(username);
        } catch (UsernameNotFoundException ufe) {
            return Mono.error(new UsernameNotFoundException(ApiErrorCode.ACCOUNT_NOT_EXIST.getMessage()));
        }
        CustomUserDetail userDetails = (CustomUserDetail) userDetailsMono.block();
        if (!passwordEncoder.matches(rawPassword, userDetails.getPassword())) {
            return Mono.error(new BadCredentialsException("密码错误"));
        }
        UsernamePasswordAuthenticationToken authenticationToken = new UsernamePasswordAuthenticationToken(userDetails, rawPassword, userDetails.getAuthorities());
        authenticationToken.setDetails(userDetails);
        SecurityContextHolder.getContext().setAuthentication(authenticationToken);
        return Mono.just(authenticationToken);
    }
}
