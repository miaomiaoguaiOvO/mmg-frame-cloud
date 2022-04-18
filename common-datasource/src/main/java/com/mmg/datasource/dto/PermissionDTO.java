package com.mmg.datasource.dto;

import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import lombok.Data;

/**
 * @Auther: fan
 * @Date: 2021/6/29
 * @Description:
 */
@Data
@ApiModel(value = "新增权限请求信息")
public class PermissionDTO {

    @ApiModelProperty(value = "权限名称", required = true)
    private String permissionName;

    @ApiModelProperty(value = "权限说明")
    private String permissionDesc;

    @ApiModelProperty(value = "拦截路径", required = true)
    private String permissionUrl;
}
