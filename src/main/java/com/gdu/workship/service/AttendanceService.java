package com.gdu.workship.service;

import java.util.Map;

import javax.servlet.http.HttpServletRequest;

import org.springframework.ui.Model;

public interface AttendanceService {

	public void getAttendancePage(int memberNo, Model model);
	public Map<String, Object> aStart(int memberNo);
	public Map<String, Object> aEnd(int memberNo);
	public Map<String, Object> search(HttpServletRequest request);
	
}
