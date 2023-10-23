package com.xcback.security.handler;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.xcback.common.constant.JWTConstant;
import com.xcback.common.entity.BackResult;
import com.xcback.common.entity.CheckResult;
import com.xcback.utils.JWTUtil;
import com.xcback.utils.RedisUtil;
import io.jsonwebtoken.Claims;
import io.jsonwebtoken.JwtException;
import org.springframework.security.core.Authentication;
import org.springframework.security.web.authentication.logout.LogoutSuccessHandler;
import org.springframework.stereotype.Component;

import javax.annotation.Resource;
import javax.servlet.ServletException;
import javax.servlet.ServletOutputStream;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;

@Component
public class LogoutSuccessHandlerImpl implements LogoutSuccessHandler {

    @Resource
    private ObjectMapper objectMapper;
    @Resource
    private RedisUtil redisUtil;


    @Override
    public void onLogoutSuccess(HttpServletRequest request,
                                HttpServletResponse response,
                                Authentication authentication) throws IOException, ServletException {
        response.setContentType("application/json;charset=utf-8");

        // 获取 jwt
        String token = request.getHeader("token");
        // 校验jwt
        CheckResult checkResult = JWTUtil.validateJWT(token);
        if(!checkResult.isSuccess()){
            switch(checkResult.getErrCode()){
                case JWTConstant.JWT_ERRCODE_NULL: throw new JwtException("Token 不存在");
                case JWTConstant.JWT_ERRCODE_EXPIRE: throw new JwtException("Token 已过期");
                case JWTConstant.JWT_ERRCODE_FAIL: throw new JwtException("Token 认证失败");
            }
        }
        Claims claims = checkResult.getClaims();
        String username = claims.getSubject();
        // 从redis中删除登录成功后删除的Token
        redisUtil.del(username+":"+"token");

        ServletOutputStream out = response.getOutputStream();
        out.write(objectMapper.writeValueAsString(BackResult.success("退出登录成功！")).getBytes());
        out.close();
    }
}
