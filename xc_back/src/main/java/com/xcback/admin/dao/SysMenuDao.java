package com.xcback.admin.dao;

import com.xcback.admin.entity.SysMenu;

import java.util.List;

public interface SysMenuDao {
    int deleteByPrimaryKey(Long id);

    int insert(SysMenu record);

    int insertSelective(SysMenu record);

    SysMenu selectByPrimaryKey(Long id);

    List<SysMenu> selectAllAsc();

    int updateByPrimaryKeySelective(SysMenu record);

    int updateByPrimaryKey(SysMenu record);

    int countByParentId(Long id);
}