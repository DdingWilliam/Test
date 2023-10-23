package com.xcback.utils;


import com.github.pagehelper.PageInfo;
import com.xcback.admin.entity.PageRequest;
import com.xcback.admin.entity.PageResult;

/**
 * 分页工具类
 * 通过 getPageResult 方法获取分页后的相关数据封装对象
 */
public class PageUtil {

    public static PageRequest PAGE_REQUEST;

    public static PageResult PAGE_RESULT;

    private PageUtil(){}

    /**
     * 获取分页后的相关数据封装对象
     * @param pageInfo
     * @return PageResult 对象
     */
    public static void setPageResult(PageInfo<?> pageInfo){
        PAGE_RESULT = PageResult.builder()
                .pageNum(pageInfo.getPageNum())
                .pageSize(pageInfo.getPageSize())
                .total(pageInfo.getTotal())
                .totalNum(pageInfo.getPages())
                .totalSize(pageInfo.getSize())
                .info(pageInfo.getList())
                .build();
    }

    /**
     * 给 PAGE_REQUEST 属性初始化
     * @param pageRequest
     */
    public static void initPageRequest(PageRequest pageRequest){
        PAGE_REQUEST = pageRequest;
    }

}
