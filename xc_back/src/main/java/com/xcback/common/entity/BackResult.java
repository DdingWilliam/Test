package com.xcback.common.entity;

import lombok.Data;

@Data
public class BackResult<T>{

    private T data;
    private Boolean msg;
    private Integer status;

    private BackResult(Integer status, Boolean msg, T data){
        this.status = status;
        this.msg = msg;
        this.data = data;
    }

    public static <T> BackResult<T> success(){
        return new BackResult<>(200,true,null);
    }
    public static <T> BackResult<T> success(T data){
        return new BackResult<>(200,true,data);
    }
    public static <T> BackResult<T> failure(Integer status, T data){
        return new BackResult<>(status,false,data);
    }
    public static <T> BackResult<T> failure(Integer status){
        return new BackResult<>(status,false,null);
    }

}
