package com.gdu.workship.controller;

import javax.servlet.http.Cookie;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;

import com.gdu.workship.service.LoginService;

import lombok.RequiredArgsConstructor;

@RequestMapping("/login")
@RequiredArgsConstructor
@Controller
public class LoginController {

	private final LoginService loginService;
	
	  /*@GetMapping("/loginTemp.do")
	  public String loginForm(HttpServletRequest request, Model model) {
	    // 요청 헤더 referer : 로그인 화면으로 이동하기 직전의 주소를 저장하는 헤더 값
	    String url = request.getHeader("referer");
	    model.addAttribute("url", url == null ? request.getContextPath() : url);
	    return "templates/loginTemp";
	  }*/
	  @PostMapping("/main.do")
	  public void login(HttpServletRequest request, HttpServletResponse response) {
		 loginService.login(request, response);
	  }
	  
	  @GetMapping("/logoutTemp.do")
	  public String logout(HttpServletRequest request, HttpServletResponse response) {
	    loginService.logout(request, response);
	    return "redirect:/";
	  }
	  @GetMapping("/findPassword.do")  // 비밀번호 찾기 화면으로 이동
	  public String findPwForm() {
	    return "login/findPassword";
	  }
	  
	  @PostMapping("/main2.do")
	  public String loginSuccess(HttpServletRequest request, HttpServletResponse response) {
	    boolean isAuthenticated = loginService.loginSuccess(request, response);

	    if (isAuthenticated) {
	      // 로그인 성공 시 메인 화면으로 리다이렉트
	      return "redirect:/main/main2";
	    } else {
	      // 로그인 실패 시 다시 로그인 페이지로 이동하거나 에러 메시지를 보여줄 수 있습니다.
	      return "redirect:/index"; // 로그인 페이지로 이동
	    }
	  }
	  


}
