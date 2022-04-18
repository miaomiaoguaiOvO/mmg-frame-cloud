package com.mmg.user.provider.controller;

import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import javax.servlet.http.HttpServletRequest;

/**
 * @Auther: fan
 * @Date: 2022/4/15
 * @Description:
 */
@RestController
@RequestMapping("/api/user")
@Api(tags = "测试模块")
public class TestController {

    @GetMapping("/provider")
    @ApiOperation(value = "Get测试")
    public String provider(HttpServletRequest request) {
        return "success provider";
    }

    @PostMapping("/provider")
    @ApiOperation(value = "Post测试")
    public String provider() {
        return "success provider";
    }
}
