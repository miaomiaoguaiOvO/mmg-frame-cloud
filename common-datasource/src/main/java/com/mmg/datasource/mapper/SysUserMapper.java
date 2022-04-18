package com.mmg.datasource.mapper;

import com.baomidou.mybatisplus.core.mapper.BaseMapper;

import com.mmg.datasource.entity.Role;
import com.mmg.datasource.entity.SysUser;
import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Param;
import reactor.core.publisher.Mono;

import java.util.List;

/**
 * <p>
 * 用户表 Mapper 接口
 * </p>
 *
 * @author fan
 * @since 2021-04-28
 */
@Mapper
public interface SysUserMapper extends BaseMapper<SysUser> {

    /**
     * 根据用户id查用户角色
     * @param userId
     * @return
     */
    List<Role> getRoleById(@Param("userId") String userId);
}
