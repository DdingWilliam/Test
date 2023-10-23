package com.xcback.admin.entity;

import java.io.Serializable;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

/**
 * sys_user_role
 * @author 
 */
@Data
@AllArgsConstructor
@NoArgsConstructor
@Builder
public class SysUserRole implements Serializable {
    /**
     * 用户角色主键ID
     */
    private Long id;

    /**
     * 用户ID
     */
    private Long userId;

    /**
     * 角色ID
     */
    private Long roleId;

    private static final long serialVersionUID = 1L;
}