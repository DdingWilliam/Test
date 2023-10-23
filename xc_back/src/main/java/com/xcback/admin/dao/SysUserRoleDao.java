package com.xcback.admin.dao;

import com.xcback.admin.entity.SysRole;
import com.xcback.admin.entity.SysUserRole;
import org.apache.ibatis.annotations.Param;

import java.util.List;

public interface SysUserRoleDao {
    int deleteBatch(Long[] ids);

    int deleteBatchByRoleId(Long[] ids);

    int insert(SysUserRole record);

    int insertSelective(SysUserRole record);

    int updateByPrimaryKeySelective(SysUserRole record);

    int updateByPrimaryKey(SysUserRole record);

    List<SysRole> getUserAuthorities(@Param("uid") Long uid);
}