package com.xcback.security.service;

import com.xcback.admin.entity.SysUser;
import com.xcback.admin.service.SysUserService;
import com.xcback.security.exceptions.UserLockedException;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.authority.AuthorityUtils;
import org.springframework.security.core.userdetails.User;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;
import org.springframework.util.ObjectUtils;
import org.springframework.util.StringUtils;

import javax.annotation.Resource;
import java.util.List;

/**
 * 自定义 UserDetailsService
 */
@Service
public class MyUserDetailService implements UserDetailsService {

    @Resource
    private SysUserService sysUserService;

    @Override
    public UserDetails loadUserByUsername(String username) throws UsernameNotFoundException {
        SysUser sysUser = sysUserService.getByUserName(username);
        if(ObjectUtils.isEmpty(sysUser)) throw new UsernameNotFoundException("用户名不存在！");
        else if(sysUser.getStatus().equals("1")) throw new UserLockedException("该用户账号已被封禁，具体联系管理员！");
        return new User(sysUser.getUsername(), sysUser.getPassword(), getUserAuthorites(sysUser.getId()));
    }

    public List<GrantedAuthority> getUserAuthorites(Long userId){
        String authorities = sysUserService.getUserAuthoritiesInfo(userId);
        return AuthorityUtils.createAuthorityList(StringUtils.tokenizeToStringArray(authorities,","));
    }
}
