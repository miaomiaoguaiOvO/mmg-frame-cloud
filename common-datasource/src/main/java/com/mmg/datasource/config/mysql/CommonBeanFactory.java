package com.mmg.datasource.config.mysql;

import com.zaxxer.hikari.HikariDataSource;
import org.springframework.boot.context.properties.ConfigurationProperties;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Primary;
import org.springframework.stereotype.Component;

/**
 * @Auther: fan
 * @Date: 2021/6/3
 * @Description: 初始化DataSource类
 */
//@Component
//public class CommonBeanFactory {
//
//    @Bean
//    @Primary
//    @ConfigurationProperties(ignoreUnknownFields = false,value = "spring.datasource.hikari")
//    public HikariDataSource dataSource(){
//        return new DataSourceEncode();
//    }
//}
