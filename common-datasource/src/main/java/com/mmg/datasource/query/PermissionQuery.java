package com.mmg.datasource.query;

import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import lombok.Data;

/**
 * @Auther: fan
 * @Date: 2021/6/29
 * @Description:
 */
@Data
@ApiModel(value = "权限列表请求信息")
public class PermissionQuery extends BasePageableQuery {

    @ApiModelProperty("权限名称")
    private String permissionName;

    @ApiModelProperty("拦截路径")
    private String permissionUrl;

}
