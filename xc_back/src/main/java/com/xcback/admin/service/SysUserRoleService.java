package com.xcback.admin.service;

import com.xcback.admin.entity.SysUserRole;

import java.util.List;

public interface SysUserRoleService {

    Integer deleteBatch(Long[] ids);

    Integer insertBatch(List<SysUserRole> sysUserRoleList);

    Integer deleteBatchByRoleId(Long[] ids);
}
