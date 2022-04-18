package com.mmg.datasource.dto;

import com.mmg.commons.config.module.CommonRequest;
import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import lombok.Data;

import javax.validation.constraints.Size;

/**
 * @Auther: fan
 * @Date: 2021/4/30
 * @Description:
 */
@Data
@ApiModel(value = "登录请求信息")
public class LoginDTO extends CommonRequest {

    @Size(min = 2, max = 15, message = "登录用户名长度在2-15")
    @ApiModelProperty(value = "用户名", required = true)
    private String username;

    @Size(min = 6, max = 20, message = "登录密码长度在6-20")
    @ApiModelProperty(value = "密码", required = true)
    private String password;
}
