package com.gdu.workship.controller;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.PostMapping;

import com.gdu.workship.service.TempService;

import lombok.RequiredArgsConstructor;

@RequiredArgsConstructor
@Controller
public class TempController {
	
	private final TempService tempService;
	
	@PostMapping("/tempLogin.do")
	public void tempLogin(HttpServletRequest request, HttpServletResponse response) {
		tempService.login(request, response);
	}
	
}
