package com.gdu.workship.mapper;

import org.apache.ibatis.annotations.Mapper;

import com.gdu.workship.domain.MemberDTO;

@Mapper
public interface LoginMapper {
	  public int updateMemberAccess(String emailId);
	  public int insertMemberAccess(String emailId);
	  public int insertAutologin(MemberDTO memberDTO);
	  public int deleteAutologin(String emailId);
	  public MemberDTO getMemberDTO(MemberDTO memberDTO);
	  public String selectAutologin(String autologinId);
	  
}
