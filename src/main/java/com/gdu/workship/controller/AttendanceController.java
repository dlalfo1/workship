package com.gdu.workship.controller;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;

@Controller
public class AttendanceController {

	@GetMapping("/attendance/attendList.html")
	public String attendList() {
		return "attendance/attendList";
	}
	
}
