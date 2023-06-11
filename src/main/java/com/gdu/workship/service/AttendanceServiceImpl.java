package com.gdu.workship.service;

import java.util.HashMap;
import java.util.Map;

import javax.servlet.http.HttpServletRequest;

import org.springframework.stereotype.Service;
import org.springframework.ui.Model;

import com.gdu.workship.mapper.AttendanceMapper;

import lombok.RequiredArgsConstructor;

@RequiredArgsConstructor
@Service
public class AttendanceServiceImpl implements AttendanceService {

	private final AttendanceMapper attendanceMapper;
	
	@Override
	public void getAttendanceList(HttpServletRequest request, Model model) {
		
	}
	
	@Override
	public Map<String, Object> aStart(int memberNo) {
		attendanceMapper.addAStartTime(memberNo);
		Map<String, Object> map = new HashMap<>();
		map.put("aStartTime", attendanceMapper.getAttendanceToday(memberNo).getAstarttime());
		return map;
	}
	
}
