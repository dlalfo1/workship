package com.gdu.workship.mapper;

import org.apache.ibatis.annotations.Mapper;

import com.gdu.workship.domain.MemberDTO;

@Mapper
public interface TempMapper {
  
	public MemberDTO getMemberDTO(MemberDTO memberDTO);
	
}
