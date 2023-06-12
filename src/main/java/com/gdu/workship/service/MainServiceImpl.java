package com.gdu.workship.service;

import java.util.HashMap;
import java.util.Map;

import org.springframework.stereotype.Service;

import com.gdu.workship.mapper.AttendanceMapper;
import com.gdu.workship.mapper.MainMapper;

import lombok.RequiredArgsConstructor;

@RequiredArgsConstructor
@Service
public class MainServiceImpl implements MainService {

	private final MainMapper mainMapper;
	private final AttendanceMapper attendanceMapper;
	
	@Override
	public Map<String, Object> astart(int memberNo) {
		mainMapper.addAStartTime(memberNo);
		Map<String, Object> map = new HashMap<>();
		map.put("aStartTime", attendanceMapper.getAttendanceToday(memberNo).getAstarttime());
		return map;
	}
	
}
