package com.xcback.admin.service.impl;

import com.xcback.admin.annotation.PageHandler;
import com.xcback.admin.dao.SysRoleDao;
import com.xcback.admin.entity.SysRole;
import com.xcback.admin.service.SysRoleService;
import org.springframework.stereotype.Service;

import javax.annotation.Resource;
import java.util.List;


@Service
public class SysRoleServiceImpl implements SysRoleService {

    @Resource
    private SysRoleDao sysRoleDao;

    @Override
    public List<SysRole> selectAll() {
        return sysRoleDao.selectAll();
    }

    @Override
    @PageHandler
    public List<SysRole> selectAllByRoleName(String roleName) {
        return sysRoleDao.selectAllByRoleName(roleName);
    }

    @Override
    public SysRole selectByRoleId(Long id) {
        return sysRoleDao.selectByPrimaryKey(id);
    }

    @Override
    public Integer save(SysRole sysRole) {
        return sysRoleDao.updateByPrimaryKeySelective(sysRole);
    }

    @Override
    public Integer insert(SysRole sysRole) {
        return sysRoleDao.insertSelective(sysRole);
    }

    @Override
    public Integer delete(Long[] ids) {
        return sysRoleDao.deleteBatch(ids);
    }
}
