package com.gdu.workship.controller;

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
	  
	  


}
