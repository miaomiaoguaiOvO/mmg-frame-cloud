package com.mmg.user.provider;

import org.mybatis.spring.annotation.MapperScan;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.cloud.client.discovery.EnableDiscoveryClient;
import org.springframework.context.annotation.ComponentScan;

/**
 * @Auther: fan
 * @Date: 2022/4/15
 * @Description:
 */
@SpringBootApplication
@EnableDiscoveryClient
@ComponentScan(basePackages = {"com.mmg.user.provider", "com.mmg.datasource"})
@MapperScan(basePackages = {"com.mmg.datasource.mapper"})
public class UserProviderApplication {
    public static void main(String[] args) {
        SpringApplication.run(UserProviderApplication.class, args);
    }
}
