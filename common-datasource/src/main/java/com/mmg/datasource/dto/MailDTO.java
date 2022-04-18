package com.mmg.datasource.dto;

import com.mmg.commons.config.module.CommonRequest;
import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import lombok.Data;

import javax.validation.constraints.Email;

/**
 * @Auther: fan
 * @Date: 2021/6/17
 * @Description:
 */
@Data
@ApiModel(value = "获取验证码邮箱")
public class MailDTO extends CommonRequest {

    @Email(message = "邮箱格式不正确")
    @ApiModelProperty(value = "邮箱",required = true)
    private String email;
}
