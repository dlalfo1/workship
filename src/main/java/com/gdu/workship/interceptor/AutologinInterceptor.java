package com.gdu.workship.interceptor;
import javax.servlet.http.Cookie;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;
import org.springframework.web.servlet.HandlerInterceptor;
import org.springframework.web.util.WebUtils;

import com.gdu.workship.mapper.LoginMapper;

@Component
public class AutologinInterceptor implements HandlerInterceptor {

  // 로그인이 안 된 상태이고,
  // 쿠키에 autologinId 값이 존재하는 경우에
  // 자동 로그인을 수행하는 인터셉터
  
  @Autowired
  private LoginMapper loginMapper;
  
  @Override
  public boolean preHandle(HttpServletRequest request, HttpServletResponse response, Object handler) throws Exception {

    HttpSession session = request.getSession();
    
    if(session != null && session.getAttribute("loginMember") == null) {  // 로그인이 되어 있는가?
      
      Cookie cookie = WebUtils.getCookie(request, "autologinId");
      
      if(cookie != null) {  // 쿠키 autologinId가 w존재하는가?
        
        String autologinId = cookie.getValue();
        String emailId = loginMapper.selectAutologin(autologinId);
        
        if(emailId != null) {
          session.setAttribute("emailId", emailId);
        }
        
      }
      
    }
    
    return true;  // 인터셉터를 동작 시킨 뒤 컨트롤러를 계속 동작시킨다.
    
  }
  
}