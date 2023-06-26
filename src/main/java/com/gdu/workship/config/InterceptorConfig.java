package com.gdu.workship.config;
import org.springframework.context.annotation.Configuration;
import org.springframework.web.servlet.config.annotation.InterceptorRegistry;
import org.springframework.web.servlet.config.annotation.WebMvcConfigurer;

import com.gdu.workship.interceptor.AutologinInterceptor;
import com.gdu.workship.interceptor.LoginCheckInterceptor;
import com.gdu.workship.interceptor.PreventLoginInterceptor;

import lombok.RequiredArgsConstructor;

@RequiredArgsConstructor
@Configuration
public class InterceptorConfig implements WebMvcConfigurer {

	private final LoginCheckInterceptor loginInterceptor;
	private final PreventLoginInterceptor preventLoginInterceptor;
	private final AutologinInterceptor autologinInterceptor;
	
	@Override
	public void addInterceptors(InterceptorRegistry registry) {
		
		registry.addInterceptor(autologinInterceptor)
			.addPathPatterns("/**");
		
		registry.addInterceptor(preventLoginInterceptor)
			.addPathPatterns("/index.html");
		
		 // 로그인 체크 인터셉터 추가하기
		// registry.addInterceptor(loginInterceptor).addPathPatterns("");
		 
	}
	
}
