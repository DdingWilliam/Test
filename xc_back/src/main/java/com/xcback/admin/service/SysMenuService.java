package com.xcback.admin.service;

import com.xcback.admin.entity.SysMenu;

import java.util.List;

public interface SysMenuService {

    List<SysMenu> buildTreeMenu(List<? extends SysMenu> sysMenuList);

    List<SysMenu> selectAllAsc();

    SysMenu selectById(Integer id);

    Integer save(SysMenu sysMenu);

    Integer insert(SysMenu sysMenu);

    Integer deleteById(Long id);

    Integer countByParentId(Long id);

}
