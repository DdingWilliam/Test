package com.xcback.admin.controller;


import com.xcback.admin.entity.SysUser;
import com.xcback.admin.service.SysUserRoleService;
import com.xcback.admin.service.SysUserService;
import com.xcback.common.constant.Constant;
import com.xcback.common.entity.BackResult;
import com.xcback.admin.entity.PageRequest;
import com.xcback.utils.DateUtil;
import com.xcback.utils.PageUtil;
import org.apache.commons.io.FileUtils;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;

import javax.annotation.Resource;
import java.io.File;
import java.io.IOException;
import java.util.Date;
import java.util.HashMap;
import java.util.Map;

/**
 * 用户 Controller 控制器
 */
@RestController
@RequestMapping("/sys/user")
public class SysUserController {

    @Resource
    private SysUserService sysUserService;

    @Resource
    private SysUserRoleService sysUserRoleService;

    @Resource
    private BCryptPasswordEncoder bCryptPasswordEncoder;

    @Value("${avatarImageFilePath}")
    private String avatarImageFilePath;

    /**
     * 保存用户信息
     * @return BackResult
     */
    @PostMapping("/save")
    @PreAuthorize("hasAuthority('system:user:add') || hasAuthority('system:user:edit')")
    public BackResult save(@RequestBody SysUser sysUser){
        sysUser.setUpdateTime(new Date());
        Integer cnt = sysUserService.save(sysUser);
        if(cnt != 1) return BackResult.failure(500,"保存失败，请联系管理员");
        return BackResult.success("保存成功!");
    }


    @PostMapping("/add")
    @PreAuthorize("hasAuthority('system:user:add') || hasAuthority('system:user:edit')")
    public BackResult insert(@RequestBody SysUser sysUser){
        sysUser.setUpdateTime(new Date());
        sysUser.setCreateTime(new Date());
        sysUser.setPassword(bCryptPasswordEncoder.encode(sysUser.getPassword()));
        Integer cnt = sysUserService.insert(sysUser);
        if(cnt != 1) return BackResult.failure(500,"添加失败");
        return BackResult.success("添加成功");
    }


    /**
     * 批量删除 |单个删除 用户信息
     * @param ids
     * @return
     */
    @Transactional
    @PostMapping("/delete")
    @PreAuthorize("hasAuthority('system:user:delete')")
    public BackResult delete(@RequestBody Long[] ids){
        Integer cnt = sysUserService.deleteBatch(ids);
        cnt += sysUserRoleService.deleteBatch(ids);
        if(cnt>0) return BackResult.success("删除成功！");
        return BackResult.failure(500,"删除失败请联系管理人员！");
    }

    /**
     * 根据主键id查询用户信息
     * @param id
     * @return
     */
    @GetMapping("/{id}")
    @PreAuthorize("hasAuthority('system:user:query')")
    public BackResult findById(@PathVariable("id") Integer id){
        SysUser sysUser = sysUserService.selectByPrimaryKey(id.longValue());
        return BackResult.success(sysUser);
    }

    /**
     * 验证用户名是否已存在
     * @param sysUser
     * @return
     */
    @PostMapping("/checkUserName")
    @PreAuthorize("hasAuthority('system:user:query')")
    public BackResult checkUserName(@RequestBody SysUser sysUser){
        if(sysUserService.getByUserName(sysUser.getUsername())!=null)
            return BackResult.failure(400,"用户名已存在！");
        return BackResult.success();
    }

    /**
     * 重置密码
     * @param id
     * @return
     */
    @GetMapping("/resetPassword/{id}")
    @PreAuthorize("hasAuthority('system:user:edit')")
    public BackResult resetPassword(@PathVariable(value = "id")Integer id){
        SysUser sysUser = sysUserService.selectByPrimaryKey(id.longValue());
        sysUser.setPassword(bCryptPasswordEncoder.encode(Constant.DEFAULT_PASSWORD));
        sysUser.setUpdateTime(new Date());
        Integer cnt = sysUserService.save(sysUser);
        if(cnt==0) return BackResult.failure(500,"密码重置有误,请联系管理员!");
        return BackResult.success("密码重置成功!");
    }

    /**
     * 修改密码
     * @param rawSysUser
     * @return
     */
    @PostMapping("/updateUserPwd")
    @PreAuthorize("hasAuthority('system:user:edit')")
    public BackResult updatePwd(@RequestBody SysUser rawSysUser){
        SysUser encodedSysUser = sysUserService.selectByPrimaryKey(rawSysUser.getId());
        if(bCryptPasswordEncoder.matches(rawSysUser.getOldPassword(),encodedSysUser.getPassword())){
            rawSysUser.setUpdateTime(new Date());
            rawSysUser.setPassword(bCryptPasswordEncoder.encode(rawSysUser.getNewPassword()));
            Integer cnt = sysUserService.save(rawSysUser);
            return (cnt==1)?(BackResult.success("修改密码成功")):(BackResult.failure(500,"修改失败，请联系管理员!"));
        }
        return BackResult.failure(400,"输入旧密码错误！");
    }

    @GetMapping("/updateStatus/{id}/status/{status}")
    @PreAuthorize("hasAuthority('system:user:edit')")
    public BackResult updateStatus(@PathVariable("id") Integer id,
                                   @PathVariable("status") Character status){
        SysUser sysUser = sysUserService.selectByPrimaryKey(id.longValue());
        sysUser.setUpdateTime(new Date());
        sysUser.setStatus(status.toString());
        Integer cnt = sysUserService.save(sysUser);
        if(cnt==0) return BackResult.failure(500,"状态改变有误,请联系管理员!");
        return BackResult.success("状态修改成功!");
    }

    @PostMapping("/uploadImage")
    @PreAuthorize("hasAuthority('system:user:edit')")
    public Map<String,Object> uploadImage(@RequestParam("file") MultipartFile file) {
        Map<String,Object> result = new HashMap<>();
        try {
            if (!file.isEmpty()) {
                // 获取文件名
                String originalFilename = file.getOriginalFilename();
                String suffixName = originalFilename.substring(originalFilename.lastIndexOf("."));
                String newFileName = DateUtil.currentTimeStr()+suffixName;
                        FileUtils.copyInputStreamToFile(file.getInputStream(), new File(avatarImageFilePath + newFileName));
                result.put("status",200);
                result.put("msg","上传成功！");
                Map<String,Object> data = new HashMap<>();
                data.put("title",newFileName);
                data.put("src","image/userAvatar/"+newFileName);
                result.put("data",data);
            }
        }catch(IOException e){
            result.put("status",500);
            result.put("msg","上传失败，请联系工作人员！");
        }finally {
            return result;
        }
    }

    /**
     * 更新头像
     * @param sysUser
     * @return
     */
    @PostMapping("/updateAvatar")
    @PreAuthorize("hasAuthority('system:user:edit')")
    public BackResult updateAvatar(@RequestBody SysUser sysUser){
        SysUser currentUser = sysUserService.selectByPrimaryKey(sysUser.getId());
        currentUser.setAvatar(sysUser.getAvatar());
        Integer cnt = sysUserService.save(currentUser);
        return (cnt==1)?(BackResult.success("头像更换成功！")):(BackResult.failure(500,"头像更换失败，请联系管理员！"));
    }


    /**
     * 根据条件分页查询用户信息
     * @param pageRequest
     * @return
     */
    @PostMapping("/list")
    @PreAuthorize("hasAuthority('system:user:query')")
    public BackResult list(@RequestBody PageRequest pageRequest){
        try{
            PageUtil.initPageRequest(pageRequest);
            sysUserService.selectAll(pageRequest.getQuery());
            return BackResult.success(PageUtil.PAGE_RESULT);
        }catch(Throwable e){
            e.printStackTrace();
            return BackResult.failure(400,"查询有误,请检查分页信息是否有误!");
        }
    }

}
