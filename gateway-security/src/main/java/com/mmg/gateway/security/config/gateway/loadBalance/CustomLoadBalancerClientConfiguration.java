package com.mmg.gateway.security.config.gateway.loadBalance;

import org.springframework.boot.autoconfigure.AutoConfigureBefore;
import org.springframework.boot.autoconfigure.condition.ConditionalOnWebApplication;
import org.springframework.boot.context.properties.EnableConfigurationProperties;
import org.springframework.cloud.client.discovery.DiscoveryClient;
import org.springframework.cloud.gateway.config.GatewayReactiveLoadBalancerClientAutoConfiguration;
import org.springframework.cloud.gateway.config.LoadBalancerProperties;
import org.springframework.cloud.gateway.filter.ReactiveLoadBalancerClientFilter;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

/**
 * @Auther: fan
 * @Date: 2022/7/4
 * @Description:
 */
@Configuration
@EnableConfigurationProperties(LoadBalancerProperties.class)
@AutoConfigureBefore(GatewayReactiveLoadBalancerClientAutoConfiguration.class)
@ConditionalOnWebApplication(type = ConditionalOnWebApplication.Type.REACTIVE)
public class CustomLoadBalancerClientConfiguration {

    @Bean
    public ILoadBalancer getCustomLoadBalancer(DiscoveryClient client) {
        return new CustomLoadBalancer(client);
    }

    @Bean
    public ReactiveLoadBalancerClientFilter gatewayLoadBalancerClientFilter(ILoadBalancer loadBalancer, LoadBalancerProperties properties) {
        return new CustomReactiveLoadBalancerClientFilter(properties, loadBalancer);
    }
}
