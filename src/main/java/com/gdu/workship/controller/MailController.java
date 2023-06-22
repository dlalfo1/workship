package com.gdu.workship.controller;

import java.util.Map;

import javax.servlet.http.HttpServletRequest;

import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;

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
	@GetMapping("/detail.html") 
	public String mailDetail(HttpServletRequest request, Model model) {
		mailService.getMailListByNo(request, model);
		return "mail/detail"; 
	}
	

	@ResponseBody
	@PostMapping(value="/updateStar.do", produces="application/json") 
	public Map<String, Object> updateStar(@RequestBody Map<String, Object> map, HttpServletRequest request) {		
		return mailService.updateStar(map, request);				
	}
	
	@ResponseBody
	@PostMapping(value="/updateStatus.do", produces="application/json") 
	public Map<String, Object> updateStatus(@RequestBody Map<String, Object> map, HttpServletRequest request) {
		return mailService.updateStatus(map, request);		
	}
	
	@ResponseBody
	@PostMapping(value="/updateToTrash.do", produces="application/json")
	public Map<String, Object> updateToTrash(@RequestBody Map<String, Object> map, HttpServletRequest request) {
		return mailService.updateToTrash(map,request);
	}
	
	@ResponseBody
	@PostMapping(value="/updateToSpam.do", produces="application/json")
	public Map<String, Object> updateToSpam(@RequestBody Map<String, Object> map, HttpServletRequest request) {
		return mailService.updateToSpam(map,request);
	}
	
	@ResponseBody
	@PostMapping(value="/deleteSentMail.do", produces="application/json")
	public Map<String, Object> deleteSentMail(@RequestBody Map<String, Object> map, HttpServletRequest request) {
		return mailService.deleteSentMail(map,request);
	}
	
	@ResponseBody
	@PostMapping(value="/deleteReceiveMail.do", produces="application/json")
	public Map<String, Object> deleteReceiveMail(@RequestBody Map<String, Object> map, HttpServletRequest request) {
		return mailService.deleteReceiveMail(map,request);
	}
	
	@ResponseBody
	@PostMapping(value="/updateSpamCancel.do", produces="application/json")
	public Map<String, Object> updateSpamCancel(@RequestBody Map<String, Object> map, HttpServletRequest request) {
		return mailService.updateSpamCancel(map,request);
	}
	
	@ResponseBody
	@PostMapping(value="/updateRemoveCancel.do", produces="application/json")
	public Map<String, Object> updateRemoveCancel(@RequestBody Map<String, Object> map, HttpServletRequest request) {
		return mailService.updateRemoveCancel(map,request);
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
