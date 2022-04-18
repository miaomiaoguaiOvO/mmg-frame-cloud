package com.mmg.datasource.entity;

import com.baomidou.mybatisplus.annotation.IdType;
import com.baomidou.mybatisplus.annotation.TableId;
import com.baomidou.mybatisplus.annotation.TableName;
import lombok.Data;
import lombok.EqualsAndHashCode;

/**
 * <p>
 * 角色-权限表
 * </p>
 *
 * @author fan
 * @since 2021-04-28
 */
@Data
@EqualsAndHashCode(callSuper = true)
@TableName(value = "role_permission")
public class RolePermission extends BaseEntity {

    private static final long serialVersionUID = 1L;

    /**
     * 角色-权限id
     */
    @TableId(type = IdType.ASSIGN_UUID)
    private String rolePermissionId;

    /**
     * 角色id
     */
    private String roleId;

    /**
     * 权限id
     */
    private String permissionId;


}
