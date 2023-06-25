package com.gdu.workship.controller;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;

@Controller
public class MvcController {
	
	@GetMapping({"/", "/index.html"})
	public String welcome() {
		return "index";
	}
	
	@GetMapping("/approval/docList.html")
	public String approvalMain() {
		return "approval/docList";
	}
	
	@GetMapping("/project/project.html")
	public String projectMain() {
		return "project/projectMain";
	}

	
}