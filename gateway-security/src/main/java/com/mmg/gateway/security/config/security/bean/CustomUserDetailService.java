package com.mmg.gateway.security.config.security.bean;

import com.baomidou.mybatisplus.core.toolkit.Wrappers;
import com.mmg.commons.config.exception.ApiErrorCode;
import com.mmg.datasource.entity.Role;
import com.mmg.datasource.entity.SysUser;
import com.mmg.datasource.mapper.SysUserMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.userdetails.ReactiveUserDetailsService;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Component;
import reactor.core.publisher.Mono;

import java.util.List;
import java.util.stream.Collectors;

/**
 * @Auther: fan
 * @Date: 2022/3/30
 * @Description:
 */
@Component
public class CustomUserDetailService implements ReactiveUserDetailsService {

    @Autowired
    private SysUserMapper sysUserMapper;

    @Override
    public Mono<UserDetails> findByUsername(String username) {
        SysUser sysUser = sysUserMapper.selectOne(Wrappers.<SysUser>lambdaQuery().eq(SysUser::getUsername, username));
        if (sysUser == null) {
            throw new UsernameNotFoundException(ApiErrorCode.ACCOUNT_NOT_EXIST.getMessage());
        }
        CustomUserDetail customUserDetail = new CustomUserDetail();
        customUserDetail.setUsername(sysUser.getUsername());
        customUserDetail.setPassword(sysUser.getPassword());
        List<Role> roleList = sysUserMapper.getRoleById(sysUser.getUserId());
        List<String> roles = roleList.stream().map(Role::getRoleName).collect(Collectors.toList());
        customUserDetail.setRoleList(roles);
        return Mono.just(customUserDetail);
    }
}
