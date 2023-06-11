package com.gdu.workship.service;

import java.util.Map;

import javax.servlet.http.HttpServletRequest;

import org.springframework.ui.Model;

public interface AttendanceService {

	public void getAttendanceList(HttpServletRequest request, Model model);
	public Map<String, Object> aStart(int memberNo);
	
}
