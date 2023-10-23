package com.xcback.security.exceptions;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.xcback.common.entity.BackResult;
import org.springframework.security.core.AuthenticationException;
import org.springframework.security.web.AuthenticationEntryPoint;
import org.springframework.stereotype.Component;

import javax.annotation.Resource;
import javax.servlet.ServletException;
import javax.servlet.ServletOutputStream;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;

/**
 * jwt 自定义认证失败的处理
 * 即在 FilterSecurityInterceptor 中授权失败了会委托给这个来进行处理
 */
@Component
public class JWTAuthenticationEntryPoint implements AuthenticationEntryPoint {

    @Resource
    private ObjectMapper objectMapper;

    @Override
    public void commence(HttpServletRequest request,
                         HttpServletResponse response,
                         AuthenticationException authException) throws IOException, ServletException {

        response.setContentType("application/json;charset=utf-8");
        ServletOutputStream out = response.getOutputStream();
        out.write(objectMapper.writeValueAsString(BackResult.failure(HttpServletResponse.SC_UNAUTHORIZED,"认证失败，请尝试重新登录！")).getBytes());
        out.close();

    }
}
