package com.gdu.workship.controller;

import javax.servlet.http.HttpServletRequest;

import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;

import com.gdu.workship.service.MemberService;

import lombok.RequiredArgsConstructor;

@RequiredArgsConstructor
@Controller
public class MemberController {

  private final MemberService memberService;
  
	@GetMapping("/member/memberList.do")
	public String memberList(HttpServletRequest request, Model model) {
	  memberService.loadMemberList(request, model);
		return "member/member";
	}
	
}
