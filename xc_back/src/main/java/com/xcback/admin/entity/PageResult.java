package com.xcback.admin.entity;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.List;

@Data
@NoArgsConstructor
@Builder
@AllArgsConstructor
public class PageResult {

    /**
     * 页码
     */
    private int pageNum;
    /**
     * 页面条数
     */
    private int pageSize;
    /**
     * 总条数,和pageinfo中的类型相互对应
     */
    private long totalSize;
    /**
     * 总页码
     */
    private int totalNum;

    private long total;
    /**
     * 数据信息
     */
    private List<?> info;

}
