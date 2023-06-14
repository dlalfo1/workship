package com.gdu.workship.service;

import org.springframework.ui.Model;

public interface AttendanceService {

	public void addDateIntoAttendance();
	public void getMonthlyAttendance(int memberNo, Model model);
	public void getAttencePage(int memberNo, Model model);
	
}
