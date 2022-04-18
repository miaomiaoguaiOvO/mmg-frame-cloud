package com.mmg.datasource.config.mybatisplus;

import com.baomidou.mybatisplus.core.handlers.MetaObjectHandler;
import com.github.pagehelper.PageHelper;
import org.apache.ibatis.reflection.MetaObject;
import org.mybatis.spring.annotation.MapperScan;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

import java.time.LocalDateTime;
import java.util.Properties;

/**
 * @Auther: fan
 * @Date: 2021/5/6
 * @Description:
 */
@Configuration
public class MybatisPlusConfig implements MetaObjectHandler {

    /**
     * page helper分页插件
     */
    @Bean
    public PageHelper pageHelper() {
        PageHelper pageHelper = new PageHelper();
        Properties properties = new Properties();
        properties.setProperty("dialect", "mysql");
        pageHelper.setProperties(properties);
        return pageHelper;
    }

    /**
     * 配置自动创建时间和更新时间
     */
    @Override
    public void insertFill(MetaObject metaObject) {
        this.strictInsertFill(metaObject, "createTime",
                LocalDateTime.class, LocalDateTime.now()); // 起始版本 3.3.0(推荐使用)
        this.strictInsertFill(metaObject, "updateTime",
                LocalDateTime.class, LocalDateTime.now());
    }
    /**
     * 配置自动更新时间
     */
    @Override
    public void updateFill(MetaObject metaObject) {
        this.strictUpdateFill(metaObject, "updateTime",
                LocalDateTime.class, LocalDateTime.now()); // 起始版本 3.3.0(推荐)
    }
}
