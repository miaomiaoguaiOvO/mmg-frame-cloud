package com.mmg.user.consumer;

import org.mybatis.spring.annotation.MapperScan;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.cloud.client.discovery.EnableDiscoveryClient;
import org.springframework.cloud.openfeign.EnableFeignClients;
import org.springframework.context.annotation.ComponentScan;

/**
 * @Auther: fan
 * @Date: 2022/4/18
 * @Description:
 */
@SpringBootApplication
@EnableDiscoveryClient
@ComponentScan(basePackages = {"com.mmg.commons", "com.mmg.datasource", "com.mmg.user.consumer"})
@MapperScan("com.mmg.datasource.mapper")
@EnableFeignClients
public class ConsumerApplication {
    public static void main(String[] args) {
        SpringApplication.run(ConsumerApplication.class, args);
    }
}
