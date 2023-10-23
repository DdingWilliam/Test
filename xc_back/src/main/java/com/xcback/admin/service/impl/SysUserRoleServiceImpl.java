package com.xcback.admin.service.impl;

import com.xcback.admin.dao.SysUserRoleDao;
import com.xcback.admin.entity.SysUserRole;
import com.xcback.admin.service.SysUserRoleService;
import org.springframework.stereotype.Service;

import javax.annotation.Resource;
import java.util.List;


@Service
public class SysUserRoleServiceImpl implements SysUserRoleService {

    @Resource
    private SysUserRoleDao sysUserRoleDao;

    @Override
    public Integer deleteBatch(Long[] ids) {
        return sysUserRoleDao.deleteBatch(ids);
    }

    @Override
    public Integer insertBatch(List<SysUserRole> sysUserRoleList) {
        int cnt = 0;
        for (SysUserRole sysUserRole : sysUserRoleList) {
            cnt += sysUserRoleDao.insertSelective(sysUserRole);
        }
        return cnt;
    }

    @Override
    public Integer deleteBatchByRoleId(Long[] ids) {
        return null;
    }
}
