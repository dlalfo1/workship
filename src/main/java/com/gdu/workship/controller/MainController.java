package com.gdu.workship.controller;

import java.util.Map;

import javax.servlet.http.HttpServletRequest;
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
		return "main/main4";
	}
	
	@ResponseBody
	@GetMapping("/getTodoList.do")
	public Map<String, Object> getTododoList(HttpServletRequest request){
		return mainService.getTodoList(request);
	}

	@ResponseBody
	@GetMapping(value="/aStart.do", produces="application/json")
	public Map<String, Object> aStart(@RequestParam("memberNo") int memberNo) {
		return mainService.aStart(memberNo);
	}
	
	@ResponseBody
	@GetMapping(value="/aEnd.do", produces="application/json")
	public Map<String, Object> aEnd(@RequestParam("memberNo") int memberNo) {
		return mainService.aEnd(memberNo);
	}

	
}
