package com.gdu.workship.mapper;

import java.util.List;

import org.apache.ibatis.annotations.Mapper;

import com.gdu.workship.domain.AttendanceDTO;
import com.gdu.workship.domain.NoticeDTO;

@Mapper
public interface MainMapper {

	// 출퇴근기록
	public AttendanceDTO getAttendanceToday(int memberNo);
	public int addAStartTime(int memberNo);
	public int addEndTime(int memberNo);
	
	// 공지사항
	public List<NoticeDTO> getRecentNoticeList();
	
}
