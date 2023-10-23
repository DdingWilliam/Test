package com.xcback.admin.service.impl;

import com.alibaba.druid.util.StringUtils;
import com.xcback.admin.annotation.PageHandler;
import com.xcback.admin.dao.SysRoleMenuDao;
import com.xcback.admin.dao.SysUserDao;
import com.xcback.admin.dao.SysUserRoleDao;
import com.xcback.admin.entity.SysMenu;
import com.xcback.admin.entity.SysRole;
import com.xcback.admin.entity.SysUser;
import com.xcback.admin.service.SysUserService;
import org.springframework.stereotype.Service;

import javax.annotation.Resource;
import java.util.HashSet;
import java.util.List;
import java.util.Set;
import java.util.StringJoiner;
import java.util.stream.Collectors;


@Service
public class SysUserServiceImpl implements SysUserService {

    @Resource
    private SysUserDao sysUserDao;

    @Resource
    private SysUserRoleDao sysUserRoleDao;

    @Resource
    private SysRoleMenuDao sysRoleMenuDao;

    @Override
    public Integer insert(SysUser record) {
        return sysUserDao.insert(record);
    }

    @Override
    public SysUser selectByPrimaryKey(Long id) {
        return sysUserDao.selectByPrimaryKey(id);
    }

    @Override
    @PageHandler
    public List<SysUser> selectAll(String username) {
        List<SysUser> sysUsers = sysUserDao.selectAll(username);
        // 注入角色
        sysUsers.forEach(sysUser->sysUser.setSysRoleList(sysUserRoleDao.getUserAuthorities(sysUser.getId())));
        return sysUsers;
    }

    @Override
    public SysUser getByUserName(String username) {
        return sysUserDao.getByUserName(username);
    }

    @Override
    public String getUserAuthoritiesInfo(Long userId) {
        final StringJoiner stringJoiner = new StringJoiner(",");
        // 根据用户id获取所有的角色信息
        List<SysRole> roles = sysUserRoleDao.getUserAuthorities(userId);
        if(roles.size() > 0){
            String roleStr = roles.stream()
                    .map(auth -> "ROLE_" + auth.getCode())
                    .collect(Collectors.joining(","));
            /*System.out.println(roleStr);*/
            stringJoiner.add(roleStr);
        }
        // 遍历所有的角色，获取所有菜单权限，而且不重复
        final Set<String> menuCodeStrSet = new HashSet<>();
        roles.forEach(role->{
            List<SysMenu> menus = sysRoleMenuDao.getMenuByRoleId(role.getId());
            menus.stream()
                    .map(menu->menu.getPerms())
                    .filter(perm->!StringUtils.isEmpty(perm))
                    .forEach(menuCodeStrSet::add);
        });
        if (menuCodeStrSet.size()>0) {
            menuCodeStrSet.forEach(stringJoiner::add);
        }
        /*System.out.println(stringJoiner.toString());*/
        return stringJoiner.toString();
    }

    @Override
    public Integer save(SysUser sysUser) {
        return sysUserDao.updateByPrimaryKeySelective(sysUser);
    }

    @Override
    public Integer deleteBatch(Long[] ids) {
        return sysUserDao.deleteBatch(ids);
    }
}
