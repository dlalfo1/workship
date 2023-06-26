package com.gdu.workship.service;

import java.time.LocalDateTime;
import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpSession;

import org.springframework.stereotype.Service;
import org.springframework.ui.Model;

import com.gdu.workship.domain.AttendanceDTO;
import com.gdu.workship.domain.MemberDTO;
import com.gdu.workship.domain.TodolistDTO;
import com.gdu.workship.mapper.AttendanceMapper;
import com.gdu.workship.mapper.MailMapper;
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
		AttendanceDTO attendanceToday = attendanceMapper.getAttendanceToday(memberNo);
		model.addAttribute("attendanceToday", attendanceToday);
		model.addAttribute("departmentName", loginMemberDTO.getDepartmentDTO().getDeptName());
		int boardCategory = loginMemberDTO.getDepartmentDTO().getDeptNo();
		model.addAttribute("deptBoardList", mainMapper.getRecentBoardList(boardCategory));
		model.addAttribute("mailCount", mainMapper.getMailNoReadCount(loginMemberDTO.getEmailId()));
		model.addAttribute("todoList", mainMapper.getRecentTodolist(memberNo));
		model.addAttribute("approvalList", mainMapper.getRecentApprovalList(memberNo));
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
	public Map<String, Object> getTodoList(HttpServletRequest request) {
		int memberNo = ((MemberDTO)(request.getSession().getAttribute("loginMember"))).getMemberNo();
		int todoState = Integer.parseInt(request.getParameter("todoState"));
		Map<String, Object> parameter = new HashMap<>();
		parameter.put("memberNo", memberNo);
		parameter.put("todoState", todoState);
		List<TodolistDTO> todolist = mainMapper.getTodoListByStatus(parameter);
		Map<String, Object> map = new HashMap<>();
		map.put("todolist", todolist);
		return map;
	}
	
}
