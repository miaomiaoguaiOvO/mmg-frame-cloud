package com.mmg.gateway.security.config.gateway.loadBalance;

import cn.hutool.core.collection.CollUtil;
import cn.hutool.core.util.RandomUtil;
import com.alibaba.fastjson.JSON;
import lombok.AllArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.cloud.client.ServiceInstance;
import org.springframework.cloud.client.discovery.DiscoveryClient;
import org.springframework.cloud.gateway.support.NotFoundException;
import org.springframework.http.server.reactive.ServerHttpRequest;

import java.util.List;

/**
 * @Auther: fan
 * @Date: 2022/7/4
 * @Description:
 */
@Slf4j
@AllArgsConstructor
public class CustomLoadBalancer implements ILoadBalancer {
    private DiscoveryClient discoveryClient;

    /**
     * 根据serviceId 筛选可用服务
     *
     * @param serviceId 服务ID
     * @param request   当前请求
     * @return
     */
    @Override
    public ServiceInstance choose(String serviceId, ServerHttpRequest request) {
        List<ServiceInstance> instances = discoveryClient.getInstances(serviceId);

        //注册中心无实例 抛出异常
        if (CollUtil.isEmpty(instances)) {
            log.warn("No instance available for {}", serviceId);
            throw new NotFoundException("No instance available for " + serviceId);
        }

//        // 获取请求version，无则随机返回可用实例
//        String reqVersion = request.getHeaders().getFirst("asda");
//        if (StrUtil.isBlank(reqVersion)) {
//            return instances.get(RandomUtil.randomInt(instances.size()));
//        }

        // 遍历可以实例元数据，若匹配则返回此实例
        for (ServiceInstance instance : instances) {

            // TODO 可以直接选择一个返回
            System.out.println("服务详情：" + JSON.toJSONString(instance));
        }
        return instances.get(RandomUtil.randomInt(instances.size()));
    }
}
