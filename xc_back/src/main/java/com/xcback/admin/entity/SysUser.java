package com.xcback.admin.entity;

import java.io.Serializable;
import java.util.Date;
import java.util.List;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

/**
 * sys_user
 * @author 
 */
@Data
@NoArgsConstructor
@AllArgsConstructor
public class SysUser extends BaseEntity implements Serializable {

    /**
     * 用户名
     */
    private String username;

    /**
     * 密码
     */
    private String password;

    /**
     * 用户头像
     */
    private String avatar;

    /**
     * 用户邮箱
     */
    private String email;

    /**
     * 手机号码
     */
    private String phonenumber;

    /**
     * 最后登录时间
     */
    private Date loginDate;

    /**
     * 帐号状态（0正常 1停用）
     */
    private String status;

    /**
     * 当前用户所拥有的角色名称集，
     * , 隔开
     */
    private String roles;

    /**
     * 旧密码
     */
    private String oldPassword;

    /**
     * 确认新密码
     */
    private String newPassword;

    /**
     * 用户所属所有角色的集合
     */
    private List<SysRole> sysRoleList;


    private static final long serialVersionUID = 1L;
}