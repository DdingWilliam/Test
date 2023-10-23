package com.xcback.security.filter;

import com.xcback.admin.entity.SysUser;
import com.xcback.admin.service.SysUserService;
import com.xcback.common.constant.JWTConstant;
import com.xcback.common.entity.CheckResult;
import com.xcback.security.service.MyUserDetailService;
import com.xcback.utils.JWTUtil;
import com.xcback.utils.RedisUtil;
import io.jsonwebtoken.Claims;
import io.jsonwebtoken.JwtException;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.web.authentication.www.BasicAuthenticationFilter;
import org.springframework.util.AntPathMatcher;
import org.springframework.util.ObjectUtils;

import javax.annotation.Resource;
import javax.servlet.FilterChain;
import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;


@Slf4j
public class JWTAuthenticationFilter extends BasicAuthenticationFilter {

    @Resource
    private SysUserService sysUserService;

    @Resource
    private MyUserDetailService myUserDetailService;

    private AntPathMatcher antPathMatcher = new AntPathMatcher();

    @Resource
    private RedisUtil redisUtil;

    private static final String[] URL_PERMITTED_LIST = {
            "/captcha",
            "/password",
            "/image/**",
    };

    public JWTAuthenticationFilter(@Autowired AuthenticationManager authenticationManager) {
        super(authenticationManager);
    }

    @Override
    protected void doFilterInternal(HttpServletRequest request,
                                    HttpServletResponse response,
                                    FilterChain chain)
            throws IOException, ServletException {

        String token = request.getHeader("token");
        log.info("token--------{}",token);
        System.out.println("请求 URL：" + request.getRequestURI());
        for(String pattern : URL_PERMITTED_LIST){
            if(antPathMatcher.match(pattern,request.getRequestURI())){
                chain.doFilter(request,response);
                return;
            }
        }
        // 验证Token，如果验证失败对失败进行处理
        CheckResult checkResult = JWTUtil.validateJWT(token);
        if(!checkResult.isSuccess()){
            switch(checkResult.getErrCode()){
                case JWTConstant.JWT_ERRCODE_NULL: throw new JwtException("Token 不存在");
                case JWTConstant.JWT_ERRCODE_EXPIRE: throw new JwtException("Token 已过期");
                case JWTConstant.JWT_ERRCODE_FAIL: throw new JwtException("Token 认证失败");
            }
        }
        // 解析jwt去获取用户名
        Claims claims = checkResult.getClaims();
        String username = claims.getSubject();
        // 判断redis中是否存在jwt
        Object res = redisUtil.get(username + ":", "token");
        if(ObjectUtils.isEmpty(res)) throw new JwtException("Token 已过期");

        SysUser sysUser = sysUserService.getByUserName(username);
        // 根据查询的用户信息封装成一个Authentication用户认证信息
        UsernamePasswordAuthenticationToken auth = new UsernamePasswordAuthenticationToken(sysUser.getUsername(),
                null,
                myUserDetailService.getUserAuthorites(sysUser.getId()));
        // 将得到的用户认证信息填入到上下文中
        SecurityContextHolder.getContext().setAuthentication(auth);
        System.out.println(SecurityContextHolder.getContext());
        // 放行
        chain.doFilter(request,response);
    }
}
