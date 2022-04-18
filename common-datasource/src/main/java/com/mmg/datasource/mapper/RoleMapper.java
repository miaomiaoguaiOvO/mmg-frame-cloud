package com.mmg.datasource.mapper;

import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import com.mmg.datasource.entity.Role;
import org.apache.ibatis.annotations.Mapper;

/**
 * <p>
 * 角色表 Mapper 接口
 * </p>
 *
 * @author fan
 * @since 2021-04-28
 */
@Mapper
public interface RoleMapper extends BaseMapper<Role> {

}
