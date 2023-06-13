package com.gdu.workship.service;

import java.sql.Time;
import java.sql.Timestamp;
import java.util.HashMap;
import java.util.Map;

import javax.servlet.http.HttpSession;

import org.springframework.stereotype.Service;
import org.springframework.ui.Model;

import com.gdu.workship.domain.AttendanceDTO;
import com.gdu.workship.domain.MemberDTO;
import com.gdu.workship.mapper.AttendanceMapper;
import com.gdu.workship.mapper.MainMapper;

import lombok.RequiredArgsConstructor;

@RequiredArgsConstructor
@Service
public class MainServiceImpl implements MainService {

	private final MainMapper mainMapper;
	private final AttendanceMapper attendanceMapper;
	
	@Override
	public void main(HttpSession session, Model model) {
		MemberDTO loginMemberDTO = (MemberDTO)session.getAttribute("loginMember");
		int memberNo = loginMemberDTO.getMemberNo();
		model.addAttribute("noticeList", mainMapper.getRecentNoticeList());
		AttendanceDTO attendanceToday = mainMapper.getAttendanceToday(memberNo);
		model.addAttribute("attendanceToday", attendanceToday);
	}
	
	/*
	@Override
	public Map<String, Object> astart(int memberNo) {
		Map<String, Object> map = new HashMap<>();
		mainMapper.addAStartTime(memberNo);
		map.put("result", attendanceMapper.getAttendanceToday(memberNo).getAstarttime());
		return map;
	}
	*/
	
	@Override
	public Map<String, Object> astart(int memberNo) {
		AttendanceDTO current = mainMapper.getAttendanceToday(memberNo);
		System.out.println("@@@@@@@@@@@" + current.getMemberNo() + "@@@@@@@@@@");
		Time time = current.getAstarttime();
		System.out.println("****************" + time + "************");
		Map<String, Object> map = new HashMap<>();
		if(time == null) {
			mainMapper.addAStartTime(memberNo);
			map.put("result", mainMapper.getAttendanceToday(memberNo).getAstarttime());
		} else {
			map.put("result", "fail");
		}
		System.out.println("%%%%%%%%%%%%%%%" + map.get("result") + "%%%%%%%%%%");
		return map;
	}
	
}
