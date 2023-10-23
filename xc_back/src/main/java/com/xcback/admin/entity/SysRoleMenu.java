package com.xcback.admin.entity;

import java.io.Serializable;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

/**
 * sys_role_menu
 * @author 
 */
@Data
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class SysRoleMenu implements Serializable {
    /**
     * 角色菜单主键ID
     */
    private Long id;

    /**
     * 角色ID
     */
    private Long roleId;

    /**
     * 菜单ID
     */
    private Long menuId;

    private static final long serialVersionUID = 1L;
}