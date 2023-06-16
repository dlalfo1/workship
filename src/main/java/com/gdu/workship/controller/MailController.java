package com.gdu.workship.controller;

import java.util.Map;

import javax.servlet.http.HttpServletRequest;

import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;

import com.gdu.workship.service.MailService;

import lombok.RequiredArgsConstructor;

@RequiredArgsConstructor
@Controller
public class MailController {
	
	// field
	private final MailService mailService;
	
	 @GetMapping("/mail/list.html")
	 public String mailList(HttpServletRequest request, Model model) {
		 mailService.getMailList(request, model);
		 return "mail/list"; 
	 }
	 
	 @GetMapping("/mail/write.html")
	 public String mailWrite() {
		 return "mail/write";
	 }
	 
	 @GetMapping("/mail/address.html")
	 public String mailAddr() {
		 return "mail/address";
	 }
}
