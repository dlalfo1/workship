package com.gdu.workship.controller;

import java.util.Map;

import javax.servlet.http.HttpServletRequest;

import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;

import com.gdu.workship.service.AttendanceService;

import lombok.RequiredArgsConstructor;

@RequiredArgsConstructor
@RequestMapping("/attendance")
@Controller
public class AttendanceController {

	private final AttendanceService attendanceService;
	
	/* @GetMapping("/attendList.html")
	public String attendList() {
		return "attendance/attendList";
	} */
	
	@GetMapping("/attendList.do")
	public void attendList(HttpServletRequest request, Model model) {
		
	}
	
	@ResponseBody
	@GetMapping(value="/aStart.do", produces="application/json")
	public Map<String, Object> aStart(@RequestParam("memberNo") int memberNo){
		return attendanceService.aStart(memberNo);
	}
	
}
