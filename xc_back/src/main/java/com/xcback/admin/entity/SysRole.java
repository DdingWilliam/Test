package com.xcback.admin.entity;

import java.io.Serializable;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

/**
 * sys_role
 * @author 
 */
@Data
@NoArgsConstructor
@AllArgsConstructor
public class SysRole extends BaseEntity implements Serializable {

    /**
     * 角色名称
     */
    private String name;

    /**
     * 角色权限字符串
     */
    private String code;


    private static final long serialVersionUID = 1L;
}