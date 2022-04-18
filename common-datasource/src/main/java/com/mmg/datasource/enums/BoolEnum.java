package com.mmg.datasource.enums;

import com.baomidou.mybatisplus.annotation.EnumValue;
import com.fasterxml.jackson.annotation.JsonValue;

/**
 * @Auther: fan
 * @Date: 2022/3/28
 * @Description:
 */
public enum BoolEnum {
    NO(0, "否"),
    YES(1, "是"),
    ;

    @EnumValue
    private final Integer code;
    @JsonValue
    private final String message;

    BoolEnum(Integer code, String message) {
        this.code = code;
        this.message = message;
    }

    public Integer getCode() {
        return code;
    }

    public String getMessage() {
        return message;
    }
}
