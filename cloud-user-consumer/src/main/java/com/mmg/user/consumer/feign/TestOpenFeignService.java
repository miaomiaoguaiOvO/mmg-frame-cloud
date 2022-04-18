package com.mmg.user.consumer.feign;

import org.springframework.cloud.openfeign.FeignClient;
import org.springframework.stereotype.Component;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;

/**
 * @Auther: fan
 * @Date: 2022/4/18
 * @Description:
 */
@Component
@FeignClient(value = "cloud-user-provider")
public interface TestOpenFeignService {

    @GetMapping(value = "/api/user/provider")
    String getProvider();

    @PostMapping(value = "/api/user/provider")
    String postProvider();
}
