package com.gdu.workship.mapper;

import java.util.List;
import java.util.Map;

import org.apache.ibatis.annotations.Mapper;

import com.gdu.workship.domain.MemberDTO;

@Mapper
public interface MemberMapper {
  public int getMemberCount();
  public List<MemberDTO> getMemberList(Map<String, Object> map);
}
