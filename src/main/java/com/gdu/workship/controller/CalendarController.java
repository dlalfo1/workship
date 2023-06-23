package com.gdu.workship.controller;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;

@RequestMapping("/calendar")
@Controller
public class CalendarController {
	
	@GetMapping({"/calendar.html"})
	public String calendar() {
		return "calendar/calendar";
	}

}