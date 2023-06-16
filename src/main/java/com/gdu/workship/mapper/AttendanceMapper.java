package com.gdu.workship.mapper;

import java.util.Date;
import java.util.List;
import java.util.Map;

import org.apache.ibatis.annotations.Mapper;

import com.gdu.workship.domain.AttendanceDTO;

@Mapper
public interface AttendanceMapper {
	
	// 출퇴근기록
	public AttendanceDTO getAttendanceToday(int memberNo);
	public int addAStartTime(Map<String, Object> map);
	public int addEndTime(Map<String, Object> map);
	
	// 월간기록
	public List<AttendanceDTO> getMonthlyAttendList(int memberNo);
	public int getMonthlyAttendanceInfo(Map<String, Object> map);
	public List<Date> getMonthlyWorktime(int memberNo);
	
	public List<AttendanceDTO> searchAttendance(Map<String, Object> map);
	
	// 스케줄러 - 1
	public List<Integer> getAllMemberNo();
	public AttendanceDTO getAttendanceYesterday(int memberNo);
	public int addAbsent(int memberNo);
	public int updateError(int memberNo);
	
}
