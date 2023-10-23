package com.xcback.security.filter;

import com.alibaba.fastjson.JSONObject;
import org.springframework.http.MediaType;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.AuthenticationServiceException;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.AuthenticationException;
import org.springframework.security.web.authentication.UsernamePasswordAuthenticationFilter;
import org.springframework.stereotype.Component;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.awt.*;
import java.io.IOException;
import java.util.Map;


public class LoginFilter extends UsernamePasswordAuthenticationFilter {

    public LoginFilter(AuthenticationManager authenticationManager) {
        super(authenticationManager);
    }

    @Override
    public Authentication attemptAuthentication(HttpServletRequest request,
                                                HttpServletResponse response)
            throws AuthenticationException {
        // 判断请求方式是否是 post
        if(! request.getMethod().equals("POST")){
            throw new AuthenticationServiceException("Authentication method not supported: " + request.getMethod());
        }
        // 判断请求内容类型是否是json类型
        if(request.getContentType().equalsIgnoreCase(MediaType.APPLICATION_JSON_VALUE)){
            try{
                // 从json中获取前端需认证的用户名和密码信息
                Map<String,Object> userInfo = JSONObject.parseObject(request.getInputStream(), Map.class);
                String username = userInfo.get(getUsernameParameter()).toString();
                String password = userInfo.get(getPasswordParameter()).toString();
                // 封装成一个Authentication对象进行认证
                UsernamePasswordAuthenticationToken auth = new UsernamePasswordAuthenticationToken(username, password);
                setDetails(request,auth);
                return getAuthenticationManager().authenticate(auth);
            }catch(IOException e) {
                throw new RuntimeException(e);
            }
        }
        return super.attemptAuthentication(request, response);
    }
}
