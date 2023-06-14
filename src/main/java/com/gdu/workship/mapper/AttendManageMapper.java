package com.gdu.workship.mapper;

import java.util.List;

import org.apache.ibatis.annotations.Mapper;

import com.gdu.workship.domain.AttendanceDTO;

@Mapper
public interface AttendManageMapper {
	
	public List<AttendanceDTO> getAllAttendanceToday();
	
}
