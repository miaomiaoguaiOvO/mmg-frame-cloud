package com.mmg.datasource.entity;

import com.baomidou.mybatisplus.annotation.IdType;
import com.baomidou.mybatisplus.annotation.TableId;
import com.baomidou.mybatisplus.annotation.TableName;
import lombok.*;

/**
 * <p>
 * 权限表
 * </p>
 *
 * @author fan
 * @since 2021-04-28
 */
@Data
@AllArgsConstructor
@NoArgsConstructor
@EqualsAndHashCode(callSuper = true)
@TableName(value = "permission")
@Builder
public class Permission extends BaseEntity {

    private static final long serialVersionUID = 1L;

    /**
     * 权限id
     */
    @TableId(type = IdType.ASSIGN_UUID)
    private String permissionId;

    /**
     * 权限名称
     */
    private String permissionName;

    /**
     * 所属服务
     */
    private String appName;

    /**
     * 拦截路径
     */
    private String permissionUrl;


}
