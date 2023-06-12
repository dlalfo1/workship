package com.gdu.workship.controller;

import java.util.Map;

import javax.servlet.http.HttpSession;

import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;

import com.gdu.workship.service.MainService;

import lombok.RequiredArgsConstructor;

@RequiredArgsConstructor
@RequestMapping("/main")
@Controller
public class MainController {

	private final MainService mainService;
	
	@GetMapping("/main.html")
	public String main(HttpSession session, Model model) {
		mainService.main(session, model);
		return "main/main";
	}
	
	@ResponseBody
	@GetMapping(value="/aStart.do", produces="application/json")
	public Map<String, Object> aStart(@RequestParam("memberNo") int memberNo) {
		return mainService.astart(memberNo);
	}
	
}
