package com.xcback.admin.config;


import org.springframework.context.annotation.Configuration;
import org.springframework.web.servlet.config.annotation.CorsRegistry;
import org.springframework.web.servlet.config.annotation.ResourceHandlerRegistry;
import org.springframework.web.servlet.config.annotation.WebMvcConfigurer;


/**
 * Web 项目配置
 */
@Configuration
public class WebAppConfig implements WebMvcConfigurer {

    /*@Override
    public void addCorsMappings(CorsRegistry registry) {
        registry.addMapping("/**")
                .allowedOrigins("http://localhost:5173/")
                .allowCredentials(true)
                .allowedHeaders("*")
                .exposedHeaders("*")
                .allowedMethods("GET", "HEAD", "POST", "PUT", "DELETE","OPTIONS")
                .maxAge(3600);
    }*/

    @Override
    public void addResourceHandlers(ResourceHandlerRegistry registry) {
        registry.addResourceHandler("/image/userAvatar/**")
                .addResourceLocations("file:G:\\cjj503\\permission-management-system-master\\xc_back\\src\\main\\resources\\image\\userAvatar")
                .addResourceLocations("classpath:/image/userAvatar/");// 项目启动之后要确认被加载
    }
}
