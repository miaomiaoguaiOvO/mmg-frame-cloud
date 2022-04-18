package com.mmg.datasource.dto;

import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import lombok.Data;
import org.hibernate.validator.constraints.Length;

import javax.validation.constraints.Email;

/**
 * @Auther: fan
 * @Date: 2021/4/30
 * @Description:
 */
@Data
@ApiModel(value = "用户注册请求信息")
public class RegisterDTO {

    @ApiModelProperty(value = "用户名",required = true)
    @Length(min = 2, max = 15, message = "长度在2-15")
    private String userName;

    @Length(min = 6, max = 20, message = "长度在6-20")
    @ApiModelProperty(value = "密码",required = true)
    private String userPass;

    @Email(message = "邮箱格式不正确")
    @ApiModelProperty(value = "邮箱",required = true)
    private String email;

    @ApiModelProperty(value = "验证码",required = true)
    private String code;



}
