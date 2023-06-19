package com.gdu.workship.controller;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;

@RequestMapping("/todolist")
@Controller
public class TodolistController {

	@GetMapping("/todomain.do")
	public String todoMain() {
		return "/todolist/todomain";
	}
	
}
