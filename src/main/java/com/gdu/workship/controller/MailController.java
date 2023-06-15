package com.gdu.workship.controller;

import javax.servlet.http.HttpServletRequest;

import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;

import com.gdu.workship.service.MailService;

import lombok.RequiredArgsConstructor;

@RequiredArgsConstructor
@RequestMapping("/mail")
@Controller
public class MailController {
	
	// field
	private final MailService mailService;
	
	 @GetMapping("/list.html")
	 public String mailList(HttpServletRequest request, Model model) {
		 mailService.getMailRlist(request, model);
		 return "mail/" + request.getParameter("mailCategory"); 
	 }
	 
	 @GetMapping("/write.html")
	 public String mailWrite() {
		 return "mail/write";
	 }
	 
	 @GetMapping("/address.html")
	 public String mailAddr() {
		 return "mail/address";
	 }
}
