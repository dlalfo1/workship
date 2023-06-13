package com.gdu.workship.service;

import java.sql.Time;
import java.util.Date;
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
			mainMapper.addAStartTime(memberNo);
			map.put("result", mainMapper.getAttendanceToday(memberNo).getAstarttime());
		} else {
			map.put("result", "fail");
		}
		return map;
	}
	
	@Override
	public Map<String, Object> aEnd(int memberNo) {
		AttendanceDTO current = mainMapper.getAttendanceToday(memberNo);
		Date startTime = current.getAstarttime();
		Date endTime = current.getAendtime();
		Map<String, Object> map = new HashMap<>();
		if(startTime == null) {
			map.put("result", "noStart");
		} else if(endTime != null) {
			map.put("result", "alreadyEnd");
		} else {
			mainMapper.addEndTime(memberNo);
			
			map.put("result", mainMapper.getAttendanceToday(memberNo).getAendtime());
		}
		return map;
	}
	
}
