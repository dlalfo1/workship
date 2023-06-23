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
	public String approvalDocList() {
		return "approval/docList";
	}
	
	@GetMapping("/approval/approvalList.html")
	public String approvalList() {
		return "approval/approvalList";
	}
	
	@GetMapping("/approval/referenceList.html")
	public String approvalReferenceList() {
		return "approval/referenceList";
	}
	
	@GetMapping("/approval/saveList.html")
	public String approvalSaveList() {
		return "approval/saveList";
	}
	
	@GetMapping("/project/project.html")
	public String projectMain() {
		return "project/projectMain";
	}

	
}