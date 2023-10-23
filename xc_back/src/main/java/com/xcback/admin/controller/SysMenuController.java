package com.xcback.admin.controller;


import com.xcback.admin.entity.SysMenu;
import com.xcback.admin.entity.SysRoleMenu;
import com.xcback.admin.service.SysMenuService;
import com.xcback.admin.service.SysRoleMenuService;
import com.xcback.common.entity.BackResult;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.bind.annotation.*;

import javax.annotation.Resource;
import java.util.Arrays;
import java.util.Date;
import java.util.List;
import java.util.stream.Collectors;

/**
 * 系统菜单Controller控制器
 */
@RestController
@RequestMapping("/sys/menu")
public class SysMenuController {

    @Resource
    private SysMenuService sysMenuService;

    @Resource
    private SysRoleMenuService sysRoleMenuService;

    @GetMapping("/treeList")
    @PreAuthorize("hasAuthority('system:menu:query')")
    public BackResult treeList(){
        List<SysMenu> menuList = sysMenuService.selectAllAsc();
        return BackResult.success(sysMenuService.buildTreeMenu(menuList));
    }

    /**
     * 根据角色id获取菜单id集
     * @param roleId
     * @return
     */
    @GetMapping("/menus/{id}")
    @PreAuthorize("hasAuthority('system:role:menu')")
    public BackResult menus(@PathVariable("id") Integer roleId){
        List<SysRoleMenu> sysRoleMenus = sysRoleMenuService.selectByRoleId(roleId);
        List<Long> menuIdList = sysRoleMenus.stream()
                .map(SysRoleMenu::getMenuId)
                .collect(Collectors.toList());
        return BackResult.success(menuIdList);
    }

    /**
     * 更新角色权限信息
     * @param roleId
     * @param menuIds
     * @return
     */
    @Transactional
    @PostMapping("/updateMenus/{id}")
    @PreAuthorize("hasAuthority('system:role:menu')")
    public BackResult updateMenus(@PathVariable("id")Long roleId,
                                  @RequestBody Long[] menuIds){

        sysRoleMenuService.deleteByRoleId(roleId);
        List<SysRoleMenu> roleMenuList = Arrays.stream(menuIds)
                .map(menuId ->
                        SysRoleMenu.builder()
                                .roleId(roleId)
                                .menuId(menuId)
                                .build()
                ).collect(Collectors.toList());
        sysRoleMenuService.insertBatch(roleMenuList);
        return BackResult.success("更新角色权限信息成功!");
    }

    /**
     * 更新菜单信息
     * @param sysMenu
     * @return
     */
    @PostMapping("/save")
    @PreAuthorize("hasAuthority('system:menu:edit')")
    public BackResult save(@RequestBody SysMenu sysMenu){
        sysMenu.setUpdateTime(new Date());
        sysMenuService.save(sysMenu);
        return BackResult.success("菜单修改成功");
    }

    /**
     * 添加菜单信息
     * @param sysMenu
     * @return
     */
    @PostMapping("/add")
    @PreAuthorize("hasAuthority('system:menu:add')")
    public BackResult insert(@RequestBody SysMenu sysMenu){
        sysMenu.setUpdateTime(new Date());
        sysMenu.setCreateTime(new Date());
        sysMenuService.insert(sysMenu);
        return BackResult.success("菜单添加成功");
    }

    /**
     * 根据id查询菜单具体信息
     * @param id
     * @return
     */
    @GetMapping("/{id}")
    @PreAuthorize("hasAuthority('system:menu:query')")
    public BackResult getMenuById(@PathVariable("id") Integer id){
        return BackResult.success(sysMenuService.selectById(id));
    }

    @PostMapping("/delete/{id}")
    public BackResult delete(@PathVariable("id") Long id){
        int cnt = sysMenuService.countByParentId(id);// 如果查询到了说明存在子元素
        if(cnt>0){
            return BackResult.failure(400,"请先删除子菜单");// 有子元素不允许删除
        }
        sysMenuService.deleteById(id);
        return BackResult.success("删除该菜单成功");
    }


}
