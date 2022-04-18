package com.mmg.datasource.mapper;

import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import com.mmg.datasource.entity.Permission;
import com.mmg.datasource.vo.RolePermissionByAppNameVO;
import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Param;

import java.util.List;

/**
 * <p>
 * 权限表 Mapper 接口
 * </p>
 *
 * @author fan
 * @since 2021-04-28
 */
@Mapper
public interface PermissionMapper extends BaseMapper<Permission> {

    List<RolePermissionByAppNameVO> getRolePermissionByAppName(@Param("appName") String appName);

}
