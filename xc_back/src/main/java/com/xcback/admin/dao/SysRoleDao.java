package com.xcback.admin.dao;

import com.xcback.admin.entity.SysRole;
import org.apache.ibatis.annotations.Param;

import java.util.List;

public interface SysRoleDao {

    int deleteBatch(Long[] ids);

    int insert(SysRole record);

    int insertSelective(SysRole record);

    SysRole selectByPrimaryKey(Long id);

    int updateByPrimaryKeySelective(SysRole record);

    int updateByPrimaryKey(SysRole record);

    List<SysRole> selectAll();

    List<SysRole> selectAllByRoleName(@Param("name") String roleName);
}