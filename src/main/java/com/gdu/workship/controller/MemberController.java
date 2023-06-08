package com.gdu.workship.controller;

import javax.servlet.http.HttpServletRequest;

import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.multipart.MultipartHttpServletRequest;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;

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
	
	@PostMapping("/member/addMember.do")
	public String addMember(MultipartHttpServletRequest request, RedirectAttributes redirectAttributes) {
	  memberService.addMember(request);
	  return "redirect:/member/member.html";
	}
	
}
