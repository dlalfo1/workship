package com.gdu.workship.controller;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;

import com.gdu.workship.service.LoginService;

@RequestMapping("/templates")
@Controller
public class LoginController {

	private LoginService loginService;
	
	  @GetMapping("/loginTemp.html")
	  public String loginForm(HttpServletRequest request, Model model) {
	    // 요청 헤더 referer : 로그인 화면으로 이동하기 직전의 주소를 저장하는 헤더 값
	    String url = request.getHeader("referer");
	    model.addAttribute("url", url == null ? request.getContextPath() : url);
	    return "templates/loginTemp";
	  }
	  @PostMapping("/loginTemp.html")
	  public void login(HttpServletRequest request, HttpServletResponse response) {
		 loginService.login(request, response);
	  }
}
