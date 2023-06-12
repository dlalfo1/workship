package com.gdu.workship.mapper;

import org.apache.ibatis.annotations.Mapper;

@Mapper
public interface MainMapper {

	public int addAStartTime(int memberNo);
	
}
