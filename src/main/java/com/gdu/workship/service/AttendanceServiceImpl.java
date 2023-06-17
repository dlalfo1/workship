package com.gdu.workship.service;

import java.text.SimpleDateFormat;
import java.time.LocalDate;
import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Optional;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpSession;

import org.springframework.stereotype.Service;
import org.springframework.ui.Model;

import com.gdu.workship.domain.AttendanceDTO;
import com.gdu.workship.domain.MemberDTO;
import com.gdu.workship.mapper.AttendanceMapper;
import com.gdu.workship.util.PageUtil;

import lombok.RequiredArgsConstructor;

@RequiredArgsConstructor
@Service
public class AttendanceServiceImpl implements AttendanceService {

	private final AttendanceMapper attendanceMapper;
	private final PageUtil pageUtil;
	
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
				if(attendNow.equals("정상") && now.getHour() >= 18) attendance = "정상";
				if(attendNow.equals("정상") && now.getHour() < 18) attendance = "조퇴";
				if(attendNow.equals("지각") && now.getHour() >= 18) attendance = "지각";
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
	public Map<String, Object> search(HttpServletRequest request) {
		HttpSession session = request.getSession();
		MemberDTO memberDTO = (MemberDTO)session.getAttribute("loginMember");
		int memberNo = memberDTO.getMemberNo();
		LocalDate now = LocalDate.now();
		String thismonthStr = LocalDate.of(now.getYear(), now.getMonthValue(), 1).toString();
		String startDateStr = request.getParameter("startDate");
		if(startDateStr.isBlank()) startDateStr = thismonthStr;
		int startyear = Integer.parseInt(startDateStr.substring(0, 4));
		int startmonth = Integer.parseInt(startDateStr.substring(5, 7));
		int startday = Integer.parseInt(startDateStr.substring(8, 10));
		LocalDate startDate = LocalDate.of(startyear, startmonth, startday);
		String endDateStr = request.getParameter("endDate");
		String nowStr = now.toString();
		if(endDateStr.isBlank()) endDateStr = nowStr;
		int endyear = Integer.parseInt(endDateStr.substring(0, 4));
		int endmonth = Integer.parseInt(endDateStr.substring(5, 7));
		int endday = Integer.parseInt(endDateStr.substring(8, 10));
		LocalDate endDate = LocalDate.of(endyear, endmonth, endday);
		String[] attendanceTemp = request.getParameterValues("attendance");
		String[] attendanceArr = attendanceTemp[0].split(",");
		String attendance = "";
		int length = attendanceArr.length;
		for(int i = 0; i < length; i++) {
			attendanceArr[i] = "'" + attendanceArr[i] + "'";
		}
		for(int i = 0; i < length - 1; i++) {
			attendance += attendanceArr[i] + ", ";
		}
		attendance += attendanceArr[length - 1];
		if(attendance.contains("지각") || attendance.contains("조퇴")) {
			attendance += ", '지각/조퇴'";
		}
		Map<String, Object> parameter = new HashMap<>();
		parameter.put("memberNo", memberNo);
		parameter.put("startDate", startDate);
		parameter.put("endDate", endDate);
		parameter.put("attendance", attendance);
		int recordPerPage = 5;
		int searchRecord = attendanceMapper.searchCount(parameter);
		Optional<String> pageStr = Optional.ofNullable(request.getParameter("page"));
		int page = Integer.parseInt(pageStr.orElse("1"));
		pageUtil.setPageUtil(page, searchRecord, recordPerPage);
		parameter.put("begin", pageUtil.getBegin());
		parameter.put("recordPerPage", recordPerPage);
		List<AttendanceDTO> list = attendanceMapper.searchAttendance(parameter);
		Map<String, Object> map = new HashMap<>();
		if(list.isEmpty()) map.put("result", "noResult");
		else {
			map.put("result", list);
			map.put("pageUtil", pageUtil);
		}
		return map;
	}
	
}
