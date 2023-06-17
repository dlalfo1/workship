package com.gdu.workship.mapper;

import java.util.List;

import org.apache.ibatis.annotations.Mapper;

import com.gdu.workship.domain.AttendanceDTO;

@Mapper
public interface AttendManageMapper {
	
	// 스케줄러
	public List<Integer> getAllMemberNo();
	public AttendanceDTO getAttendanceYesterday(int memberNo);
	public int addAbsent(int memberNo);
	public int updateError(int memberNo);
	
	public List<AttendanceDTO> getAllAttendanceToday();
	
}
