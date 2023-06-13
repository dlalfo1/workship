package com.gdu.workship.batch;

import org.springframework.scheduling.annotation.EnableScheduling;
import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.stereotype.Component;

import com.gdu.workship.service.AttendanceService;

import lombok.RequiredArgsConstructor;

@Component
@RequiredArgsConstructor
@EnableScheduling
public class AttendanceScheduler {

	private final AttendanceService attendanceService;
	
	@Scheduled(cron="0 58 12 ? * MON-FRI")
	 public void addDateIntoAttendance() {
		 attendanceService.addDateIntoAttendance();
	 }
	
}
