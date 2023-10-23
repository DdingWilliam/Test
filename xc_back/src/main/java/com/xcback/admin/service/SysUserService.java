package com.xcback.admin.service;

import com.xcback.admin.entity.SysUser;

import java.util.List;

public interface SysUserService {

    Integer insert(SysUser record);

    SysUser selectByPrimaryKey(Long id);

    List<SysUser> selectAll(String username);

    SysUser getByUserName(String username);

    String getUserAuthoritiesInfo(Long userId);

    Integer save(SysUser sysUser);

    Integer deleteBatch(Long[] ids);
}
