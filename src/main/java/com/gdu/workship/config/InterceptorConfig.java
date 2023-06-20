package com.gdu.workship.config;
import java.util.ArrayList;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Configuration;
import org.springframework.web.servlet.config.annotation.InterceptorRegistry;
import org.springframework.web.servlet.config.annotation.WebMvcConfigurer;

import com.gdu.workship.interceptor.AutologinInterceptor;
import com.gdu.workship.interceptor.LoginInterceptor;
import com.gdu.workship.interceptor.PreventLoginInterceptor;

import lombok.RequiredArgsConstructor;

@RequiredArgsConstructor
@Configuration
public class InterceptorConfig implements WebMvcConfigurer {

	@Autowired
	private final LoginInterceptor loginInterceptor;
	private final PreventLoginInterceptor preventLoginInterceptor;
	private final AutologinInterceptor autologinInterceptor;
	
	@Override
	public void addInterceptors(InterceptorRegistry registry) {
		List<String> preventLogin = new ArrayList<>();
		preventLogin.add("/index.html");
		registry.addInterceptor(autologinInterceptor).addPathPatterns("/**");
		registry.addInterceptor(preventLoginInterceptor).addPathPatterns(preventLogin);
		// 로그인 체크 인터셉터 추가하기
		// registry.addInterceptor(loginCheckInterceptor).addPathPatterns("");
	}
	
}
