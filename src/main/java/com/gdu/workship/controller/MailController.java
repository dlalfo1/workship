package com.gdu.workship.controller;

import javax.servlet.http.HttpServletRequest;

import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;

import com.gdu.workship.service.MailService;

import lombok.RequiredArgsConstructor;

@RequestMapping("/mail")
@RequiredArgsConstructor
@Controller
public class MailController {
	
	// field
	private final MailService mailService;
	
	@GetMapping("/list.html")
	public String mailSearchList(HttpServletRequest request, Model model) {
		mailService.getMailList(request, model);
		return "mail/list";
	}	 

	/*
	 * @GetMapping("/detail.html") public String mailDetail(HttpServletRequest
	 * request, Model model) { model.addAttribute("mail",
	 * mailService.getMailByNo(request)); return "mail/detail"; }
	 * 
	 */
	 @GetMapping("/write.html")
	 public String mailWrite() {
		 return "mail/write";
	 }
	 
	 @GetMapping("/address.html")
	 public String mailAddr() {
		 return "mail/address";
	 }
}
