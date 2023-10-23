package com.xcback.security.config;


import com.xcback.security.exceptions.JWTAuthenticationEntryPoint;
import com.xcback.security.filter.CaptchaFilter;
import com.xcback.security.filter.JWTAuthenticationFilter;
import com.xcback.security.handler.LoginFailureHandler;
import com.xcback.security.filter.LoginFilter;
import com.xcback.security.handler.LoginSuccessHandler;
import com.xcback.security.service.MyUserDetailService;
import org.springframework.context.annotation.Bean;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.config.annotation.authentication.builders.AuthenticationManagerBuilder;
import org.springframework.security.config.annotation.method.configuration.EnableGlobalMethodSecurity;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.config.http.SessionCreationPolicy;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.security.web.SecurityFilterChain;
import org.springframework.security.web.authentication.logout.LogoutFilter;
import org.springframework.security.web.authentication.logout.LogoutSuccessHandler;
import org.springframework.web.cors.CorsConfiguration;
import org.springframework.web.cors.CorsConfigurationSource;
import org.springframework.web.cors.UrlBasedCorsConfigurationSource;

import javax.annotation.Resource;

@EnableWebSecurity
@EnableGlobalMethodSecurity(prePostEnabled = true)
public class  SecurityConfig {

    @Resource
    private MyUserDetailService myUserDetailService;

    @Resource
    private LoginSuccessHandler loginSuccessHandler;

    @Resource
    private LoginFailureHandler loginFailureHandler;

    @Resource
    private LogoutSuccessHandler logoutSuccessHandlerImpl;

    @Resource
    private JWTAuthenticationEntryPoint jwtAuthenticationEntryPoint;

    @Resource
    private CaptchaFilter captchaFilter;


    private static final String[] URL_PERMITTED_LIST = {
            "/api/auth/login",
            "/api/auth/logout",
            "/captcha",
            "/password",
            "/image/**",
            "/test/**"
    };

    @Bean
    public SecurityFilterChain securityFilterChain(HttpSecurity http) throws Exception {
        // 不开启跨站请求伪造的防护
        http.csrf(csrfConfig->csrfConfig.disable());

        // 关闭session
        // 关闭原因：
        // 1. 前后端进行通信，每个请求都是一个独立的事务，开启session管理可能会使得信息无法共享
        // 2. 采用session管理的话，多个用户进行访问服务器端的内存会占用过高，这是因为session的废除机制是超时机制
        // 3. 采用session管理功能，这也是一个安全漏洞
        // 这里使用jwt（Java web token）令牌的方式进行认证，不需要session了
        http.sessionManagement().sessionCreationPolicy(SessionCreationPolicy.STATELESS);

        // 配置跨域，允许跨域访问
        http.cors().configurationSource(this.corsConfigurationSource());


        // 配置拦截规则
        http.authorizeRequests()
                .antMatchers(URL_PERMITTED_LIST).permitAll()
                .anyRequest()
                .authenticated();



        // 异常处理配置
        http.exceptionHandling()
                        .authenticationEntryPoint(jwtAuthenticationEntryPoint);

        // 退出系统的一些配置，logout 退出系统的url，和成功处理器
        http.logout()
                .logoutUrl("/api/auth/logout")
                        .logoutSuccessHandler(logoutSuccessHandlerImpl);

        // 添加自定义的认证过滤器 LoginFilter
        // 前后端登录信息以JSON数据传送，替代Security中的form表单形式
        http.addFilter(loginFilter(http));

        // 添加自定义的过滤器-基本认证过滤器，让每个请求都得经过jwt认证...
        http.addFilter(jwtAuthenticationFilter(http));

        // 添加自定义过滤器-基本过滤器，验证验证码
        http.addFilterAfter(captchaFilter, LogoutFilter.class);
        return http.build();
    }


    @Bean
    public JWTAuthenticationFilter jwtAuthenticationFilter(HttpSecurity http) throws Exception {
        return new JWTAuthenticationFilter(authenticationManager(http));
    }


    @Bean
    public LoginFilter loginFilter(HttpSecurity http) throws Exception {
        LoginFilter loginFilter = new LoginFilter(authenticationManager(http));
        loginFilter.setFilterProcessesUrl("/api/auth/login"); // 设置认证的url，默认是 /login
        loginFilter.setUsernameParameter("username");  // 设置参数用户名名称
        loginFilter.setPasswordParameter("password");  // 设置参数密码名称
        loginFilter.setAuthenticationSuccessHandler(loginSuccessHandler); // 设置认证成功后的处理器
        loginFilter.setAuthenticationFailureHandler(loginFailureHandler); // 设置认证失败后的处理器
        return loginFilter;
    }

    @Bean
    public AuthenticationManager authenticationManager(HttpSecurity http) throws Exception {
        return http.getSharedObject(AuthenticationManagerBuilder.class)
                .userDetailsService(myUserDetailService)
                .passwordEncoder(passwordEncoder())
                .and().build();
    }

    @Bean
    public PasswordEncoder passwordEncoder(){
        return new BCryptPasswordEncoder();
    }

    private CorsConfigurationSource corsConfigurationSource(){
        CorsConfiguration corsConfiguration = new CorsConfiguration();
        corsConfiguration.setAllowCredentials(true);
        corsConfiguration.addAllowedHeader("*"); // 这个得加上，一些复杂的请求方式会带有header，不加上跨域会失效。
        corsConfiguration.addAllowedMethod("*");
        corsConfiguration.addExposedHeader("*");
        corsConfiguration.addAllowedOriginPattern("http://127.0.0.1:5173/");
        UrlBasedCorsConfigurationSource source = new UrlBasedCorsConfigurationSource();
        source.registerCorsConfiguration("/**",corsConfiguration);
        return source;
    }



}
