package com.gdu.workship.batch;

import org.springframework.scheduling.annotation.EnableScheduling;
import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.stereotype.Component;

import com.gdu.workship.service.AttendManageService;

import lombok.RequiredArgsConstructor;

@Component
@RequiredArgsConstructor
@EnableScheduling
public class AttendanceScheduler {

	private final AttendManageService attendManageService;
	
	@Scheduled(cron="0 0 3 ? * MON-FRI")
	 public void addDateIntoAttendance() {
		 attendManageService.updateAllScehduler();
	 }
	
}
