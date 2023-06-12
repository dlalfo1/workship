package com.gdu.workship.mapper;

import java.util.List;

import org.apache.ibatis.annotations.Mapper;

import com.gdu.workship.domain.AttendanceDTO;
import com.gdu.workship.domain.NoticeDTO;

@Mapper
public interface MainMapper {

	public int addAStartTime(int memberNo);
	public List<NoticeDTO> getRecentNoticeList();
	public AttendanceDTO getAttendanceToday(int memberNo);
	
}
