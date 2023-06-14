package com.gdu.workship.service;

import java.text.SimpleDateFormat;
import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Optional;

import javax.servlet.http.HttpServletRequest;

import org.springframework.stereotype.Service;
import org.springframework.ui.Model;

import com.gdu.workship.domain.AttendanceDTO;
import com.gdu.workship.mapper.AttendManageMapper;
import com.gdu.workship.mapper.AttendanceMapper;

import lombok.RequiredArgsConstructor;

@RequiredArgsConstructor
@Service
public class AttendanceServiceImpl implements AttendanceService {

	private final AttendanceMapper attendanceMapper;
	private final AttendManageMapper attendManageMapper;
	
	@Override
	public void getAttendancePage(int memberNo, Model model) {

		model.addAttribute("attendanceToday", attendanceMapper.getAttendanceToday(memberNo));
		model.addAttribute("attendanceList", attendanceMapper.getMonthlyAttendList(memberNo));

		Map<String, Object> parameter = new HashMap<>();
		parameter.put("memberNo", memberNo);
		List<Integer> list = new ArrayList<>();
		String[] queries = {"정상", "지각", "조퇴", "결근"};
		for(int i = 0, length = queries.length; i < length; i++) {
			parameter.put("query", queries[i]);
			list.add(attendanceMapper.getMonthlyAttendanceInfo(parameter));
		}
		model.addAttribute("attendanceInfo", list);
		
		List<Date> worktimeList = attendanceMapper.getMonthlyWorktime(memberNo);
		int hours = 0, minutes = 0, seconds = 0;
		for(int i = 0, length = worktimeList.size(); i < length; i++) {
			SimpleDateFormat formatter = new SimpleDateFormat("HHmmss");
			String date = formatter.format(worktimeList.get(i));
			hours += Integer.parseInt(date.substring(0,2));
			minutes += Integer.parseInt(date.substring(2,4));
			seconds += Integer.parseInt(date.substring(4,6));
		}
		while(seconds >= 60) {
			seconds -= 60; minutes += 1;
		}
		while(minutes >= 60) {
			minutes -= 60; hours += 1;
		}
		String worktime = hours + "시간 " + minutes + "분";
		model.addAttribute("worktime", worktime);
		
	}
	
	@Override
	public Map<String, Object> aStart(int memberNo) {
		AttendanceDTO current = attendanceMapper.getAttendanceToday(memberNo);
		Map<String, Object> map = new HashMap<>();
		if(current == null) {
			Map<String, Object> parameter = new HashMap<>();
			String attendance = "";
			LocalDateTime now = LocalDateTime.now();
			if(now.getHour() < 9) attendance = "정상";
			else attendance = "지각";
			parameter.put("memberNo", memberNo);
			parameter.put("time", now);
			parameter.put("attendance", attendance);
			attendanceMapper.addAStartTime(parameter);
			map.put("result", attendanceMapper.getAttendanceToday(memberNo).getAstarttime().toString());
		} else {
			map.put("result", "fail");
		}
		return map;
	}

	@Override
	public Map<String, Object> aEnd(int memberNo) {
		AttendanceDTO current = attendanceMapper.getAttendanceToday(memberNo);
		Map<String, Object> map = new HashMap<>();
		
		if(current == null) {
			map.put("result", "noStart");
		} else {
			Date startTime = current.getAstarttime();
			Date endTime = current.getAendtime();
			String attendNow = current.getAttendance();
			
			if(startTime == null) {
				map.put("result", "noStart");
			} else if(endTime != null) {
				map.put("result", "alreadyEnd");
			} else {
				Map<String, Object> parameter = new HashMap<>();
				String attendance = "";
				LocalDateTime now = LocalDateTime.now();
				if(attendNow.equals("정상") && now.getHour() > 6) attendance = "정상";
				if(attendNow.equals("정상") && now.getHour() < 6) attendance = "조퇴";
				if(attendNow.equals("지각") && now.getHour() > 18) attendance = "지각";
				if(attendNow.equals("지각") && now.getHour() < 18) attendance = "지각/조퇴";
				parameter.put("memberNo", memberNo);
				parameter.put("time", now);
				parameter.put("attendance", attendance);
				attendanceMapper.addEndTime(parameter);
				map.put("result", attendanceMapper.getAttendanceToday(memberNo).getAendtime().toString());
			}
		}
		return map;
	}
	
	@Override
	public void updateAllScehduler() {
		List<Integer> memberNoList = attendanceMapper.getAllMemberNo();
		for(int memberNo : memberNoList) {
			AttendanceDTO attendanceDTO = attendanceMapper.getAttendanceYesterday(memberNo);
			if(attendanceDTO == null) attendanceMapper.addAbsent(memberNo);
			else {
				Date aEndTime = attendanceDTO.getAendtime();
				if(aEndTime == null) attendanceMapper.updateError(memberNo);
			}
		}
	}
	
	@Override
	public void getAttendManagePage(HttpServletRequest request, Model model) {
		
	}
}
