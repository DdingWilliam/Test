package com.xcback.admin.controller;


import com.xcback.admin.entity.SysUser;
import com.xcback.admin.service.SysUserService;
import com.xcback.common.entity.BackResult;
import com.xcback.utils.JWTUtil;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.*;

import javax.annotation.Resource;
import java.util.HashMap;
import java.util.Map;

@RestController
@RequestMapping("/test")
public class TestController {

    @Resource
    private SysUserService sysUserService;

    @GetMapping
    @PreAuthorize("hasAuthority('test')")
    public BackResult test(Long id){
        SysUser sysUser = sysUserService.selectByPrimaryKey(id);
        return BackResult.success(sysUser);
    }

    /*@PreAuthorize("hasRole('ROLE_test')")
    @GetMapping("/user/list")
    public BackResult userList(@RequestHeader(required = false) String token){
        if(StringUtil.isNullOrEmpty(token)) return BackResult.failure(401,"没有权限访问");
        List<SysUser> sysUsers = sysUserService.selectAll();
        return BackResult.success(sysUsers);
    }*/

    @GetMapping("/login")
    public BackResult login(){
        String jwt = JWTUtil.createJWT("myz");
        Map<String,Object> token =  new HashMap<>();
        token.put("token",jwt);
        return BackResult.success(token);
    }

}
