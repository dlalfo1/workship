package com.gdu.workship.batch;

import com.gdu.workship.service.AttendanceService;

import lombok.RequiredArgsConstructor;

@RequiredArgsConstructor
public class AttendanceScheduler {

	private final AttendanceService attendanceService;
	
	 public void addDateIntoAttendance() {
		 attendanceService.addDateIntoAttendance();
	 }
	
}
