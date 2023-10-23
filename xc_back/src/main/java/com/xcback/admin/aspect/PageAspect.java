package com.xcback.admin.aspect;


import com.github.pagehelper.PageHelper;
import com.github.pagehelper.PageInfo;
import com.xcback.utils.PageUtil;
import org.aspectj.lang.ProceedingJoinPoint;
import org.aspectj.lang.annotation.Around;
import org.aspectj.lang.annotation.Aspect;
import org.aspectj.lang.annotation.Pointcut;
import org.springframework.stereotype.Component;

import java.util.List;

@Component
@Aspect
public class PageAspect {

    @Pointcut("@annotation(com.xcback.admin.annotation.PageHandler)")
    public void pagePointCut(){}

    @Around("pagePointCut()")
    public void pageResult(ProceedingJoinPoint joinPoint) throws Throwable {
        // 开启分页
        PageHelper.startPage(PageUtil.PAGE_REQUEST.getPageNum(),PageUtil.PAGE_REQUEST.getPageSize());
        // 得到分页数据
        List proceed = (List) joinPoint.proceed();
        // 将分页结果赋值封装到PageUtil中
        PageUtil.setPageResult(new PageInfo<>(proceed));
    }

}
