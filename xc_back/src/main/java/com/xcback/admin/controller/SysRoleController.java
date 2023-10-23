package com.xcback.admin.controller;


import com.xcback.admin.entity.PageRequest;
import com.xcback.admin.entity.SysRole;
import com.xcback.admin.entity.SysUserRole;
import com.xcback.admin.service.SysRoleService;
import com.xcback.admin.service.SysUserRoleService;
import com.xcback.common.entity.BackResult;
import com.xcback.utils.PageUtil;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.bind.annotation.*;

import javax.annotation.Resource;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Date;
import java.util.List;

@RestController
@RequestMapping("/sys/role")
public class SysRoleController {

    @Resource
    private SysRoleService sysRoleService;

    @Resource
    private SysUserRoleService sysUserRoleService;

    @GetMapping("/listAll")
    @PreAuthorize("hasAuthority('system:role:query')")
    public BackResult listAll(){
        List<SysRole> roleList = sysRoleService.selectAll();
        return BackResult.success(roleList);
    }


    @Transactional
    @PostMapping("/grantRole/{id}")
    @PreAuthorize("hasAuthority('system:user:role')")
    public BackResult grantRole(@PathVariable("id") Long userId, @RequestBody Long[] roleIds){
        sysUserRoleService.deleteBatch(new Long[]{userId});
        List<SysUserRole> userRoleList = new ArrayList<>();
        Arrays.stream(roleIds).forEach(roleId->
            userRoleList.add(SysUserRole.builder()
                    .userId(userId)
                    .roleId(roleId)
                    .build())
        );
        sysUserRoleService.insertBatch(userRoleList);
        return BackResult.success("角色分配成功!");
    }

    /**
     * 根据条件分页查询角色信息
     * @param pageRequest
     * @return
     */
    @PostMapping("/list")
    @PreAuthorize("hasAuthority('system:user:query')")
    public BackResult list(@RequestBody PageRequest pageRequest){
        PageUtil.initPageRequest(pageRequest);
        sysRoleService.selectAllByRoleName(pageRequest.getQuery());
        return BackResult.success(PageUtil.PAGE_RESULT);
    }


    /**
     * 更新角色信息
     * @param sysRole
     * @return
     */
    @PostMapping("/save")
    @PreAuthorize("hasAuthority('system:role:add') || hasAuthority('system:role:edit')")
    public BackResult save(@RequestBody SysRole sysRole){
        sysRole.setUpdateTime(new Date());
        Integer cnt = sysRoleService.save(sysRole);
        if(cnt==1) return BackResult.success("保存角色信息成功!");
        return BackResult.failure(500,"保存失败,请联系管理员");
    }

    /**
     * 添加角色信息
     * @param sysRole
     * @return
     */
    @PostMapping("/add")
    @PreAuthorize("hasAuthority('system:role:add')")
    public BackResult insert(@RequestBody SysRole sysRole){
        sysRole.setCreateTime(new Date());
        sysRole.setUpdateTime(new Date());
        Integer cnt = sysRoleService.insert(sysRole);
        if(cnt==1) return BackResult.success("添加信息成功!");
        return BackResult.failure(500,"添加角色信息失败,请联系管理员");
    }


    /**
     * 根据角色 Id 查询角色信息
     * @param roleId
     * @return
     */
    @GetMapping("/{id}")
    @PreAuthorize("hasAuthority('system:role:query')")
    public BackResult getSysRoleById(@PathVariable("id") Long roleId){
        return BackResult.success(sysRoleService.selectByRoleId(roleId));
    }

    @Transactional
    @PreAuthorize("hasAuthority('system:role:delete')")
    @PostMapping("/delete")
    public BackResult delete(@RequestBody Long[] ids){
        sysRoleService.delete(ids);
        sysUserRoleService.deleteBatchByRoleId(ids);
        return BackResult.success("删除成功!");
    }


}
