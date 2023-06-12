package com.gdu.workship.mapper;

import java.util.List;

import org.apache.ibatis.annotations.Mapper;

import com.gdu.workship.domain.AttendanceDTO;

@Mapper
public interface AttendanceMapper {

	public List<Integer> getAllMemberNo();
	public void addDateIntoAttendance(int memberNo);
	public int addAStartTime(int memberNo);
	public AttendanceDTO getAttendanceToday(int memberNo);
	public List<AttendanceDTO> getMonthlyAttendList(int memberNo);
}
