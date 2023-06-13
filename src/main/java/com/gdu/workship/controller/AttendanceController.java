package com.gdu.workship.controller;

import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;

import com.gdu.workship.service.AttendanceService;

import lombok.RequiredArgsConstructor;

@RequiredArgsConstructor
@RequestMapping("/attendance")
@Controller
public class AttendanceController {

	private final AttendanceService attendanceService;
	
	@GetMapping("/attendList.do")
	public String attendList(@RequestParam("memberNo") int memberNo, Model model) {
		attendanceService.getAttendList(memberNo, model);
		return "attendance/attendList";
	}
	
}
