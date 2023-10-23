package com.xcback.admin.dao;

import com.xcback.admin.entity.SysUser;
import org.apache.ibatis.annotations.Param;

import java.util.List;

public interface SysUserDao {

    int deleteBatch(@Param("ids") Long[] ids);

    int insert(SysUser record);

    int insertSelective(SysUser record);
    List<SysUser> selectAll(String username);
    SysUser getByUserName(String username);

    SysUser selectByPrimaryKey(Long id);

    int updateByPrimaryKeySelective(SysUser record);

    int updateByPrimaryKey(SysUser record);
}