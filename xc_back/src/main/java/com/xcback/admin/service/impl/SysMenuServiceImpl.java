package com.xcback.admin.service.impl;

import com.xcback.admin.dao.SysMenuDao;
import com.xcback.admin.entity.SysMenu;
import com.xcback.admin.service.SysMenuService;
import org.springframework.stereotype.Service;

import javax.annotation.Resource;
import java.util.ArrayList;
import java.util.List;


@Service
public class SysMenuServiceImpl implements SysMenuService {

    @Resource
    private SysMenuDao sysMenuDao;

    /**
     * 生成多级菜单，即父-》子菜单
     * @param sysMenuList
     * @return
     */
    @Override
    public List<SysMenu> buildTreeMenu(List<? extends SysMenu> sysMenuList) {
        List<SysMenu> result = new ArrayList<>();
        for (SysMenu sysMenu : sysMenuList) {
            // 寻 sysMenu 的子菜单
            for (SysMenu menu : sysMenuList) {
                if (menu.getParentId() == sysMenu.getId()) {
                    sysMenu.getChildren().add(menu);
                }
            }
            // 如果父id为0，表示其是树的根节点
            if (sysMenu.getParentId() == 0) {
                result.add(sysMenu);
            }
        }
        return result;
    }

    @Override
    public List<SysMenu> selectAllAsc() {
        return sysMenuDao.selectAllAsc();
    }

    @Override
    public SysMenu selectById(Integer id) {
        return sysMenuDao.selectByPrimaryKey(id.longValue());
    }

    @Override
    public Integer save(SysMenu sysMenu) {
        return sysMenuDao.updateByPrimaryKeySelective(sysMenu);
    }

    @Override
    public Integer insert(SysMenu sysMenu) {
        return sysMenuDao.insertSelective(sysMenu);
    }

    @Override
    public Integer deleteById(Long id) {
        return sysMenuDao.deleteByPrimaryKey(id);
    }

    @Override
    public Integer countByParentId(Long id) {
        return sysMenuDao.countByParentId(id);
    }
}
