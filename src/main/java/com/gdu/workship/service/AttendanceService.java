package com.gdu.workship.service;

import javax.servlet.http.HttpServletRequest;

import org.springframework.ui.Model;

public interface AttendanceService {

	public void getAttendanceList(HttpServletRequest request, Model model);
	public void getMonthlyAttendance(int memberNo, Model model);
	public void getAttendList2(int memberNo, Model model);
	
}
