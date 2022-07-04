package com.mmg.gateway.security.config.gateway.loadBalance;

import org.springframework.cloud.client.ServiceInstance;
import org.springframework.http.server.reactive.ServerHttpRequest;

/**
 * @Auther: fan
 * @Date: 2022/7/4
 * @Description:
 */
public interface ILoadBalancer {
    ServiceInstance choose(String serviceId, ServerHttpRequest request);
}
