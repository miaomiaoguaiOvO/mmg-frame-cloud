package com.mmg.user.consumer.controller;

import com.mmg.user.consumer.feign.TestOpenFeignService;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import javax.annotation.Resource;
import javax.servlet.http.HttpServletRequest;

/**
 * @Auther: fan
 * @Date: 2022/4/18
 * @Description:
 */
@RestController
@RequestMapping("/api/user")
@Api(tags = "消费测试模块")
public class TestOpenFeignController {

    @Resource
    private TestOpenFeignService service;

    @GetMapping("/consumer")
    @ApiOperation(value = "消费Get测试")
    public String test(HttpServletRequest request) {
        return service.getProvider();
    }

    @PostMapping("/consumer")
    @ApiOperation(value = "消费Post测试")
    public String test() {
        return service.postProvider();
    }

}
