package com.xcback.admin.service;

import com.xcback.admin.entity.SysRole;

import java.util.List;

public interface SysRoleService {

    List<SysRole> selectAll();
    List<SysRole> selectAllByRoleName(String roleName);
    SysRole selectByRoleId(Long id);
    Integer save(SysRole sysRole);
    Integer insert(SysRole sysRole);
    Integer delete(Long[] ids);

}
