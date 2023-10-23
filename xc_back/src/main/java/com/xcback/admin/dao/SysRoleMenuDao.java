package com.xcback.admin.dao;

import com.xcback.admin.entity.SysMenu;
import com.xcback.admin.entity.SysRoleMenu;
import org.apache.ibatis.annotations.Param;

import java.util.List;

public interface SysRoleMenuDao {
    int deleteByPrimaryKey(Long id);

    int insert(SysRoleMenu record);

    int insertSelective(SysRoleMenu record);

    int updateByPrimaryKeySelective(SysRoleMenu record);

    int updateByPrimaryKey(SysRoleMenu record);

    List<SysMenu> getMenuByRoleId(@Param("rid") Long rid);

    List<SysRoleMenu> selectByRoleId(Integer roleId);

    int deleteByRoleId(Integer roleId);

}