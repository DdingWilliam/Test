package com.xcback.security.handler;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.xcback.admin.dao.SysRoleMenuDao;
import com.xcback.admin.dao.SysUserDao;
import com.xcback.admin.dao.SysUserRoleDao;
import com.xcback.admin.entity.SysMenu;
import com.xcback.admin.entity.SysRole;
import com.xcback.admin.entity.SysUser;
import com.xcback.admin.service.SysMenuService;
import com.xcback.common.constant.Constant;
import com.xcback.common.entity.BackResult;
import com.xcback.utils.JWTUtil;
import com.xcback.utils.RedisUtil;
import lombok.extern.slf4j.Slf4j;
import org.springframework.security.core.Authentication;
import org.springframework.security.web.authentication.AuthenticationSuccessHandler;
import org.springframework.stereotype.Component;

import javax.annotation.Resource;
import javax.servlet.ServletException;
import javax.servlet.ServletOutputStream;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.util.*;
import java.util.stream.Collectors;

@Component
@Slf4j
public class LoginSuccessHandler implements AuthenticationSuccessHandler {

    @Resource
    private ObjectMapper objectMapper;
    @Resource
    private SysUserDao sysUserDao;
    @Resource
    private SysUserRoleDao sysUserRoleDao;
    @Resource
    private SysRoleMenuDao sysRoleMenuDao;
    @Resource
    private SysMenuService sysMenuService;
    @Resource
    private RedisUtil redisUtil;

    @Override
    public void onAuthenticationSuccess(HttpServletRequest request,
                                        HttpServletResponse response,
                                        Authentication authentication) throws IOException, ServletException {
        Map<String,Object> resultData = new HashMap<>();
        // 设置响应编码格式
        response.setContentType("json/application;charset=utf-8");
        // 获取用户名
        String username = authentication.getName();
        SysUser currentUser = sysUserDao.getByUserName(username);  // 当前用户
        // 生成 jwt
        String jwt = JWTUtil.createJWT(username);
        // 并将 jwt 存入 redis 中，设置过期时间是为一小时，注意：要和jwt的过期时间保持一致
        redisUtil.set(username.trim()+":","token",jwt,60*60);
        log.info(username+":token:{}",jwt);

        ServletOutputStream out = response.getOutputStream();

        // 获取当前用户的所有角色
        List<SysRole> roleList = sysUserRoleDao.getUserAuthorities(currentUser.getId());
        // 获取当前角色名
        String rolesNameStr = roleList.stream().map(SysRole::getName).collect(Collectors.joining(","));
        currentUser.setRoles(rolesNameStr);

        // 获取当前用户的所有菜单
        final Set<SysMenu> menuSet = new HashSet<SysMenu>();
        for (SysRole sysRole : roleList) {
            List<SysMenu> menuList = sysRoleMenuDao.getMenuByRoleId(sysRole.getId());
            menuList.stream().forEach(menuSet::add);
        }
        List<SysMenu> sysMenuList = new ArrayList<>(menuSet); // 转成集合List
        sysMenuList.sort(Comparator.comparing(SysMenu::getOrderNum));

        List<SysMenu> sysMenus = sysMenuService.buildTreeMenu(sysMenuList); // 生成多级菜单
        log.info("多级菜单->{}",sysMenus);

        resultData.put("jwt",jwt);
        resultData.put("user",currentUser);
        resultData.put("menus",sysMenus);
        resultData.put(Constant.AUTHORITY_KEY,authentication.getAuthorities());
        out.write(objectMapper.writeValueAsString(BackResult.success(resultData)).getBytes());
        out.close();
    }
}
