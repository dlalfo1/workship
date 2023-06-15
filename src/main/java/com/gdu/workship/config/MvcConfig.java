package com.gdu.workship.config;
import org.springframework.context.annotation.Configuration;
import org.springframework.web.servlet.config.annotation.InterceptorRegistry;
import org.springframework.web.servlet.config.annotation.WebMvcConfigurer;

import com.gdu.workship.interceptor.LoginInterceptor;

@Configuration
public class MvcConfig implements WebMvcConfigurer {

    @Override
    public void addInterceptors(InterceptorRegistry registry) {
    	 registry.addInterceptor(new LoginInterceptor())
         .addPathPatterns("/**") // 인터셉터를 적용할 경로 패턴을 지정합니다.
         .excludePathPatterns("/css/*") // 인터셉터에서 제외할 경로 패턴을 지정합니다.
    	 .excludePathPatterns("/js/*") 
    	 .excludePathPatterns("/images/*")
    	 .excludePathPatterns("/pages/*")
    	 ; // 인터셉터에서 제외할 경로 패턴을 지정합니다.
    }
}
