package com.gdu.workship.service;

import java.time.LocalDateTime;
import java.util.Date;
import java.util.HashMap;
import java.util.Map;

import javax.servlet.http.HttpSession;

import org.springframework.stereotype.Service;
import org.springframework.ui.Model;

import com.gdu.workship.domain.AttendanceDTO;
import com.gdu.workship.domain.MemberDTO;
import com.gdu.workship.mapper.MainMapper;

import lombok.RequiredArgsConstructor;

@RequiredArgsConstructor
@Service
public class MainServiceImpl implements MainService {

	private final MainMapper mainMapper;
	
	@Override
	public void main(HttpSession session, Model model) {
		MemberDTO loginMemberDTO = (MemberDTO)session.getAttribute("loginMember");
		int memberNo = loginMemberDTO.getMemberNo();
		model.addAttribute("noticeList", mainMapper.getRecentNoticeList());
		AttendanceDTO attendanceToday = mainMapper.getAttendanceToday(memberNo);
		model.addAttribute("attendanceToday", attendanceToday);
	}
	
	@Override
	public Map<String, Object> aStart(int memberNo) {
		AttendanceDTO current = mainMapper.getAttendanceToday(memberNo);
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
			mainMapper.addAStartTime(parameter);
			map.put("result", mainMapper.getAttendanceToday(memberNo).getAstarttime().toString());
		} else {
			map.put("result", "fail");
		}
		return map;
	}
	/*
	@Override
	public Map<String, Object> aEnd(int memberNo) {
		AttendanceDTO current = mainMapper.getAttendanceToday(memberNo);
		Date startTime = current.getAstarttime();
		Date endTime = current.getAendtime();
		String attendNow = current.getAttendance();
		Map<String, Object> map = new HashMap<>();
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
			if(attendNow.equals("지각") && now.getHour() > 6) attendance = "지각";
			if(attendNow.equals("지각") && now.getHour() < 6) attendance = "지각/조퇴";
			parameter.put("memberNo", memberNo);
			parameter.put("time", now);
			parameter.put("attendance", attendance);
			mainMapper.addEndTime(parameter);
			map.put("result", mainMapper.getAttendanceToday(memberNo).getAendtime().toString());
		}
		return map;
	}
	*/
	
	@Override
	public Map<String, Object> aEnd(int memberNo) {
		AttendanceDTO current = mainMapper.getAttendanceToday(memberNo);
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
				mainMapper.addEndTime(parameter);
				map.put("result", mainMapper.getAttendanceToday(memberNo).getAendtime().toString());
			}
		}
		return map;
	}
	
}
