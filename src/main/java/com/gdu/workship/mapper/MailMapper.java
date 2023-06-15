package com.gdu.workship.mapper;

import java.util.List;
import java.util.Map;

import org.apache.ibatis.annotations.Mapper;

import com.gdu.workship.domain.MailBcDTO;
import com.gdu.workship.domain.MailCcDTO;
import com.gdu.workship.domain.MailToDTO;

@Mapper
public interface MailMapper {
	
	/* 받은 메일함 */
	public List<MailToDTO> getMailToRlist(Map<String, Object> map);
	public int getMailToRlistSearchCount(Map<String, Object> map);
	public List<MailCcDTO> getMailCcRlist(Map<String, Object> map);
	public int getMailCcRlistSearchCount(Map<String, Object> map);
	public List<MailBcDTO> getMailBcRlist(Map<String, Object> map);
	public int getMailBcRlistSearchCount(Map<String, Object> map);
}
