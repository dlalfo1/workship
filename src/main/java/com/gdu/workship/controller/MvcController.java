package com.gdu.workship.controller;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;

@Controller
public class MvcController {

	@GetMapping("/loginTemp.html")
	public String loginTemp() {
		return "loginTemp";
	}
	
	@GetMapping("/loginTemp2.html")
	public String loginTemp2() {
		return "temp/loginTemp2";
	}
	
	@GetMapping("/temp.html")
	public String temp() {
		return "temp/temp";
	}
	
	@GetMapping({"/", "/index.html"})
	public String welcome() {
		return "index";
	}
	
	@GetMapping("/mail/rlist.html")
	public String mailMain() {
		return "mail/rlist";
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
	
	@GetMapping("/notice/noticeMain.html")
	public String noticeMain() {
		return "notice/noticeMain";
	}
	
	@GetMapping("/member/memberCreate.html")
	public String memberCreate() {
		return "member/memberCreate";
	}
	
	@GetMapping("/member/member.html")
	public String member() {
		return "member/member";
	}
	
	@GetMapping("/department/dept.html")
	public String department() {
		return "department/dept";
	}
	
	@GetMapping("/report/report.html")
	public String report() {
		return "report/report";
	}
	
	@GetMapping("/board/board.html")
	public String board() {
		return "board/board";
	}
	
}
