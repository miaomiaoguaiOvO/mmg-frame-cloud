package com.mmg.gateway.security;

import lombok.extern.slf4j.Slf4j;
import org.mybatis.spring.annotation.MapperScan;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.cloud.client.discovery.EnableDiscoveryClient;
import org.springframework.context.annotation.ComponentScan;

/**
 * @Auther: fan
 * @Date: 2022/3/28
 * @Description:
 */
@SpringBootApplication
@EnableDiscoveryClient
@ComponentScan(basePackages = {"com.mmg.gateway.security", "com.mmg.datasource"})
@MapperScan(basePackages = {"com.mmg.datasource.mapper"})
@Slf4j
public class GatewaySecurityApplication {
    public static void main(String[] args) {
        SpringApplication.run(GatewaySecurityApplication.class, args);
        log.info("访问接口文档----->{}", "http://localhost/swagger-ui/index.html");
    }
}
