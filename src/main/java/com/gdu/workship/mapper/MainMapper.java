package com.gdu.workship.mapper;

import java.util.List;

import org.apache.ibatis.annotations.Mapper;

import com.gdu.workship.domain.NoticeDTO;

@Mapper
public interface MainMapper {
	
	public List<NoticeDTO> getRecentNoticeList();
	
}
