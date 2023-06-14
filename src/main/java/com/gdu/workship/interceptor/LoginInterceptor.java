package com.gdu.workship.interceptor;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.springframework.web.servlet.HandlerInterceptor;
import org.springframework.web.servlet.ModelAndView;

public class LoginInterceptor implements HandlerInterceptor {

    @Override
    public boolean preHandle(HttpServletRequest request, HttpServletResponse response, Object handler)
            throws Exception {
        // 로그인 여부를 체크하고 로직을 처리합니다.
        // 로그인이 필요한 페이지에 접근하는 경우 로그인 페이지로 리다이렉트하거나 오류 처리할 수 있습니다.
        return true; // 로그인 체크 성공 시 true 반환
    }

    @Override
    public void postHandle(HttpServletRequest request, HttpServletResponse response, Object handler,
            ModelAndView modelAndView) throws Exception {
        // 요청 처리 후 추가 작업을 수행합니다.
    }

    @Override
    public void afterCompletion(HttpServletRequest request, HttpServletResponse response, Object handler,
            Exception ex) throws Exception {
        // 요청 완료 후 추가 작업을 수행합니다.
    }
}
