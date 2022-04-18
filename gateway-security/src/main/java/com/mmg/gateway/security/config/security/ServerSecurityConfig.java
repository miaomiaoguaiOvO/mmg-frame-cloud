package com.mmg.gateway.security.config.security;

import com.mmg.gateway.security.config.security.manager.CustomSecurityContextRepository;
import com.mmg.gateway.security.config.security.manager.CustomServerAuthenticationConverter;
import com.mmg.gateway.security.config.security.handler.DefaultAccessDeniedHandler;
import com.mmg.gateway.security.config.security.handler.DefaultAuthenticationEntryPoint;
import com.mmg.gateway.security.config.security.handler.DefaultAuthenticationFailureHandler;
import com.mmg.gateway.security.config.security.handler.DefaultAuthenticationSuccessHandler;
import com.mmg.gateway.security.config.security.manager.CustomReactiveAuthenticationManager;
import com.mmg.gateway.security.config.security.manager.CustomReactiveAuthorizationManager;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Bean;
import org.springframework.http.HttpMethod;
import org.springframework.security.config.annotation.web.reactive.EnableWebFluxSecurity;
import org.springframework.security.config.web.server.SecurityWebFiltersOrder;
import org.springframework.security.config.web.server.ServerHttpSecurity;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.security.web.server.SecurityWebFilterChain;
import org.springframework.security.web.server.authentication.AuthenticationWebFilter;
import org.springframework.security.web.server.util.matcher.ServerWebExchangeMatchers;

/**
 * @Auther: fan
 * @Date: 2022/3/30
 * @Description:
 */
@EnableWebFluxSecurity
public class ServerSecurityConfig {

    @Autowired
    private CustomReactiveAuthenticationManager reactiveAuthenticationManager;
    @Autowired
    private DefaultAuthenticationSuccessHandler authenticationSuccessHandler;
    @Autowired
    private DefaultAuthenticationFailureHandler authenticationFailureHandler;
    @Autowired
    private CustomReactiveAuthorizationManager reactiveAuthorizationManager;
    @Autowired
    private DefaultAuthenticationEntryPoint authenticationEntryPoint;
    @Autowired
    private DefaultAccessDeniedHandler accessDeniedHandler;
    @Autowired
    private CustomServerAuthenticationConverter authenticationConverter;
    @Autowired
    private CustomSecurityContextRepository securityContextRepository;

    @Bean
    public PasswordEncoder passwordEncoder() {
        return new BCryptPasswordEncoder();
    }

    @Bean
    public SecurityWebFilterChain webFluxSecurityFilterChain(ServerHttpSecurity httpSecurity) {

        httpSecurity
                .csrf().disable()
                .httpBasic().disable()
                .formLogin().disable()
                .securityContextRepository(securityContextRepository)
                .authorizeExchange().anyExchange().access(reactiveAuthorizationManager)
                .and()
                .exceptionHandling()
                .accessDeniedHandler(accessDeniedHandler)
                .and()
                .exceptionHandling()
                .authenticationEntryPoint(authenticationEntryPoint)
                .and()
                .addFilterAt(authenticationWebFilter(), SecurityWebFiltersOrder.AUTHENTICATION)
        ;
        return httpSecurity.build();
    }

    private AuthenticationWebFilter authenticationWebFilter() {
        AuthenticationWebFilter filter = new AuthenticationWebFilter(reactiveAuthenticationManager);
        filter.setSecurityContextRepository(securityContextRepository);
        filter.setServerAuthenticationConverter(authenticationConverter);
        filter.setAuthenticationSuccessHandler(authenticationSuccessHandler);
        filter.setAuthenticationFailureHandler(authenticationFailureHandler);
        filter.setRequiresAuthenticationMatcher(
                ServerWebExchangeMatchers.pathMatchers(HttpMethod.POST, "/login")
        );

        return filter;
    }


}