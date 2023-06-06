package com.gdu.workship.controller;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;

@Controller
public class NoticeBoardController {

	@GetMapping("/notice/write.html")
	public String write() {
		return "notice/noticeWrite";
	}
	
}
