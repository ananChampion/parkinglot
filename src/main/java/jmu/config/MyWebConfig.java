package jmu.config;

import jmu.interceptor.LoginHandlerInterceptor;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Configuration;
import org.springframework.web.servlet.config.annotation.InterceptorRegistry;
import org.springframework.web.servlet.config.annotation.WebMvcConfigurer;

@Configuration
public class MyWebConfig implements WebMvcConfigurer {
    //注入一个LoginHandlerInterceptor
    @Autowired
    private LoginHandlerInterceptor loginHandlerInterceptor;
    @Override
    public void addInterceptors(InterceptorRegistry registry) {

        String[] url = new String[]{
                "/user/login",
                "/user/goLogin",
                "/user/home",
                "/user/goRegister",
                "/user/register",
                "/admin/goLogin",
                "/admin/login",
                "/record/insertRecord",
                "/pay/insert",
                "/static/**",
                "/user/logout",
                "/",
                "/error",
                "/user/userType"
        };


        //除了url请求地址以外，都被拦截
        registry.addInterceptor(loginHandlerInterceptor).addPathPatterns("/**").excludePathPatterns(url);
    }

}
