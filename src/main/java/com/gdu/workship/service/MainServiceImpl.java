package com.gdu.workship.service;

import java.util.HashMap;
import java.util.Map;

import javax.servlet.http.HttpSession;

import org.springframework.stereotype.Service;
import org.springframework.ui.Model;

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
		model.addAttribute("attendanceToday", mainMapper.getAttendanceToday(memberNo));
	}
	
	@Override
	public Map<String, Object> astart(int memberNo) {
		mainMapper.addAStartTime(memberNo);
		Map<String, Object> map = new HashMap<>();
		map.put("aStartTime", attendanceMapper.getAttendanceToday(memberNo).getAstarttime());
		return map;
	}
	
}
