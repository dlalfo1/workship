package com.gdu.workship.controller;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;

@RequestMapping("/vacation")
@Controller
public class VacationController {
	
	@GetMapping("/vacationList.html")
	public String vacationList() {
		return "vacation/vacationList";
	}

}
