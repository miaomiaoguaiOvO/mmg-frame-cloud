package com.mmg.datasource.entity;

import com.baomidou.mybatisplus.annotation.IdType;
import com.baomidou.mybatisplus.annotation.TableId;
import com.baomidou.mybatisplus.annotation.TableName;
import lombok.Data;
import lombok.EqualsAndHashCode;

/**
 * <p>
 * 用户-角色表
 * </p>
 *
 * @author fan
 * @since 2021-04-28
 */
@Data
@EqualsAndHashCode(callSuper = true)
@TableName("user_role")
public class UserRole extends BaseEntity {

    private static final long serialVersionUID = 1L;

    /**
     * 用户-角色id
     */
    @TableId(type = IdType.ASSIGN_UUID)
    private String userRoleId;

    /**
     * 用户id
     */
    private String userId;

    /**
     * 角色id
     */
    private String roleId;


}
