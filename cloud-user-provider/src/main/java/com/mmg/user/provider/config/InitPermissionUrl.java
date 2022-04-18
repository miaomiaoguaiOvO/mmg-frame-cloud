package com.mmg.user.provider.config;

import com.baomidou.mybatisplus.core.toolkit.Wrappers;
import com.google.common.collect.Lists;
import com.mmg.commons.config.utils.ConstantUtil;
import com.mmg.datasource.entity.Permission;
import com.mmg.datasource.entity.RolePermission;
import com.mmg.datasource.mapper.PermissionMapper;
import com.mmg.datasource.mapper.RolePermissionMapper;
import com.mmg.datasource.vo.RolePermissionByAppNameVO;
import io.swagger.annotations.ApiOperation;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.boot.CommandLineRunner;
import org.springframework.data.redis.core.RedisTemplate;
import org.springframework.stereotype.Component;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.context.WebApplicationContext;
import org.springframework.web.servlet.mvc.condition.PatternsRequestCondition;
import org.springframework.web.servlet.mvc.condition.RequestMethodsRequestCondition;
import org.springframework.web.servlet.mvc.method.annotation.RequestMappingHandlerMapping;

import java.util.HashMap;
import java.util.Iterator;
import java.util.List;
import java.util.Map;

/**
 * @Auther: fan
 * @Date: 2022/4/15
 * @Description: 初始化加载所有权限列表到数据库和缓存
 */
@Slf4j
@Component
public class InitPermissionUrl implements CommandLineRunner {

    @Autowired
    private WebApplicationContext applicationContext;

    @Autowired
    private PermissionMapper permissionMapper;

    @Autowired
    private RolePermissionMapper rolePermissionMapper;

    @Autowired
    private RedisTemplate<String, Object> redisTemplate;

    @Value("${spring.application.name}")
    private String serverName;

    @Override
    public void run(String... args) {
        RequestMappingHandlerMapping mapping = applicationContext.getBean(RequestMappingHandlerMapping.class);
        List<Permission> permissionList = Lists.newArrayList();
        mapping.getHandlerMethods().forEach((info, method) -> {
            //permissionName
            Permission permission = new Permission();
            ApiOperation apiOperation = method.getMethod().getAnnotation(ApiOperation.class);
            if (apiOperation != null) {
                permission.setPermissionName(apiOperation.value());
            } else {
                return;
            }
            //appName
            permission.setAppName(serverName);
            //method+url
            String requestMethod = "";
            RequestMethodsRequestCondition methodsCondition = info.getMethodsCondition();
            for (RequestMethod methodsConditionMethod : methodsCondition.getMethods()) {
                requestMethod = methodsConditionMethod.name();
            }
            String url = "";
            PatternsRequestCondition patternsCondition = info.getPatternsCondition();
            for (String pattern : patternsCondition.getPatterns()) {
                url = pattern;
            }
            permission.setPermissionUrl(requestMethod.concat(url));

            permissionList.add(permission);
        });
        //当前数据库中的权限列表
        Map<String, Permission> permissionMap = new HashMap<>();
        List<Permission> permissions = permissionMapper.selectList(Wrappers.<Permission>lambdaQuery().eq(Permission::getAppName, serverName));
        for (Permission permission : permissions) {
            permissionMap.put(permission.getPermissionUrl(), permission);
        }
        Iterator<Permission> iterator = permissionList.iterator();
        while (iterator.hasNext()) {
            Permission current = iterator.next();
            String key = current.getPermissionUrl();
            if (permissionMap.get(key) != null) {
                Permission permission = permissionMap.remove(key);
                if (permission.getPermissionName() != null && !permission.getPermissionName().equals(current.getPermissionName())) {
                    permission.setPermissionName(current.getPermissionName());
                    permissionMapper.updateById(permission);
                }
                iterator.remove();
            } else {
                permissionMapper.insert(current);
            }
        }
        permissionMap.forEach((url, permission) -> {
            rolePermissionMapper.delete(Wrappers.<RolePermission>lambdaQuery().eq(RolePermission::getPermissionId, permission.getPermissionId()));
            permissionMapper.deleteById(permission.getPermissionId());
        });
        List<RolePermissionByAppNameVO> rolePermissionByAppName = permissionMapper.getRolePermissionByAppName(serverName);
        Map<String, List<String>> map = new HashMap<>();
        rolePermissionByAppName.forEach(item -> {
            String permissionUrl = item.getPermissionUrl();
            String method = permissionUrl.substring(0, permissionUrl.indexOf("/"));
            String route = permissionUrl.substring(method.length());
            map.put(method.concat("/").concat(serverName).concat(route), item.getRoleList());
        });
        redisTemplate.opsForHash().putAll(ConstantUtil.CACHE_ROLE_KEY + "_" + serverName, map);
    }
}
