package com.gdu.workship.controller;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;

@RequestMapping("/mail")
@Controller
public class MailController {

	 @GetMapping("/write.html")
	 public String writeMail() {
		 return "mail/write";
	 }
	 
	 @GetMapping("/noread.html")
	 public String noreadMail() {
		 return "mail/noread";
	 }
	 
	 @GetMapping("/starred.html")
	 public String starredMail() {
		 return "mail/starred";
	 }
	 
	 @GetMapping("slist.html")
	 public String sendMail() {
		 return "mail/slist";
	 }
	 
	 @GetMapping("dlist.html")
	 public String draftMail() {
		 return "mail/dlist";
	 }
	 
	 @GetMapping("plist.html")
	 public String spamMail() {
		 return "mail/plist";
	 }
	 
	 @GetMapping("tlist.html")
	public String trashMail() {
		 return "mail/tlist";
	 }
	 
	 @GetMapping("address.html")
	 public String mailAddress() {
		 return "mail/address";
	 }
	 
	 @GetMapping("sent.html")
	public String sentMail() {
		 return "mail/sent";
	 }
	 
	 @PostMapping("add.do")
	 public String addMail() {
		 return "mail/sent";
	 }
	 
	/* @GetMapping("/search.do") */
	 
}
