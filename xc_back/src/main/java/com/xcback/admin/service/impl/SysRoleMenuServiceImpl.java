package com.xcback.admin.service.impl;

import com.xcback.admin.dao.SysRoleMenuDao;
import com.xcback.admin.entity.SysRoleMenu;
import com.xcback.admin.service.SysRoleMenuService;
import org.springframework.stereotype.Service;

import javax.annotation.Resource;
import java.util.List;


@Service
public class SysRoleMenuServiceImpl implements SysRoleMenuService {

    @Resource
    private SysRoleMenuDao sysRoleMenuDao;

    @Override
    public List<SysRoleMenu> selectByRoleId(Integer roleId) {
        return sysRoleMenuDao.selectByRoleId(roleId);
    }

    @Override
    public Integer deleteByRoleId(Long roleId) {
        return sysRoleMenuDao.deleteByRoleId(roleId.intValue());
    }

    @Override
    public Integer insertBatch(List<SysRoleMenu> roleMenuList) {
        int cnt = 0;
        for (SysRoleMenu sysRoleMenu : roleMenuList) {
            cnt += sysRoleMenuDao.insertSelective(sysRoleMenu);
        }
        return cnt;
    }
}
