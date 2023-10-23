package com.xcback.admin.service;

import com.xcback.admin.entity.SysRoleMenu;

import java.util.List;

public interface SysRoleMenuService {

    List<SysRoleMenu> selectByRoleId(Integer roleId);
    Integer deleteByRoleId(Long roleId);
    Integer insertBatch(List<SysRoleMenu> roleMenuList);

}
