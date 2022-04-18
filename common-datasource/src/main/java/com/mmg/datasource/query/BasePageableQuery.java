package com.mmg.datasource.query;


import com.mmg.commons.config.module.CommonRequest;
import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import lombok.Data;

/**
 * @Auther: fan
 * @Date: 2021/6/29
 * @Description: 分页查询基类
 */
@Data
@ApiModel(value = "分页搜索")
public class BasePageableQuery extends CommonRequest {

    @ApiModelProperty(value = "页码，默认值为1")
    private int pageNum = 1;

    @ApiModelProperty(value = "页大小")
    private int pageSize = 10;
}
