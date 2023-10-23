package com.xcback.admin.entity;


import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
public class PageRequest {
    /**
     * 页码
     */
    private int pageNum;
    /**
     * 每页个数
     */
    private int pageSize;

    /**
     * 查询参数
     */
    private String query;
}
