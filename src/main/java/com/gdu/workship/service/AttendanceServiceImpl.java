package com.gdu.workship.service;

import org.springframework.stereotype.Service;
import org.springframework.ui.Model;

import com.gdu.workship.mapper.AttendanceMapper;

import lombok.RequiredArgsConstructor;

@RequiredArgsConstructor
@Service
public class AttendanceServiceImpl implements AttendanceService {

	private final AttendanceMapper attendanceMapper;
	
	@Override
	public void addDateIntoAttendance() {
		for(int memberNo : attendanceMapper.getAllMemberNo()) {
			attendanceMapper.addDateIntoAttendance(memberNo);
		}
	}
	
	@Override
	public void getAttendList(int memberNo, Model model) {
		model.addAttribute("attendToday", attendanceMapper.getAttendanceToday(memberNo));
		model.addAttribute("attendanceList", attendanceMapper.getMonthlyAttendList(memberNo));
	}
	
	@Override
	public void getMonthlyAttendance(int memberNo, Model model) {
		model.addAttribute("attendance", attendanceMapper.getMonthlyAttendList(memberNo));
	}
}
