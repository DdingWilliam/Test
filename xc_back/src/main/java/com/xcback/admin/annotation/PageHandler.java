package com.xcback.admin.annotation;


import java.lang.annotation.*;


/**
 * 用于分页 AOP 的注解
 * 这里面属性项目中没有用到，用的是封装的PageRequest
 */
@Target(ElementType.METHOD)
@Retention(RetentionPolicy.RUNTIME)
@Documented
public @interface PageHandler {

    /**
     * 页码
     */
    public int pageNum() default 1;

    /**
     * 页数
     */
    public int pageSize() default -1;

}
