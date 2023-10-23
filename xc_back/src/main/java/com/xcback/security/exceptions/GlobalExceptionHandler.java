package com.xcback.security.exceptions;


import com.xcback.common.entity.BackResult;
import lombok.extern.slf4j.Slf4j;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.RestControllerAdvice;

/**
 * 自定义全局异常处理，当出现RuntimeException时，将错误信息封装到BackResult中进行返回
 * 这里主要针对的是自定义的LoginFilter和自定义的MyUserDetailService内部实现抛出的异常
 */
@RestControllerAdvice
@Slf4j
public class GlobalExceptionHandler {

    @ExceptionHandler(RuntimeException.class)
    public BackResult handler(RuntimeException e){
        log.error("运行时异常：---------------{}",e.getMessage());
        return BackResult.failure(500,e.getMessage());
    }

}
