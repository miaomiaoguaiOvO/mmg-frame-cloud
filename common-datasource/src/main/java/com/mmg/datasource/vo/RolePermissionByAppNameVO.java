package com.mmg.datasource.vo;

import lombok.Data;

import java.util.List;

/**
 * @Auther: fan
 * @Date: 2022/4/15
 * @Description:
 */
@Data
public class RolePermissionByAppNameVO {
    private String permissionUrl;
    private List<String> roleList;
}
