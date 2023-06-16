package com.gdu.workship.mapper;

import java.util.List;
import java.util.Map;

import org.apache.ibatis.annotations.Mapper;

import com.gdu.workship.domain.MailDTO;

@Mapper
public interface MailMapper {
	public int getMailCount(Map<String, Object> map);
	public List<MailDTO> getMailList(Map<String, Object> map);
}
