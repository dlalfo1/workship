package com.gdu.workship.mapper;

import java.util.List;
import java.util.Map;

import org.apache.ibatis.annotations.Mapper;

import com.gdu.workship.domain.MailDTO;

@Mapper
public interface MailMapper {
	
	/* 메일 개수 체크 */
	public int getMailTotalCount(Map<String, Object> map);
	public int getMailNoReadCount(Map<String, Object> map);	
	public int getMailStarCount(Map<String, Object> map);	
	public int getMailCount(Map<String, Object> map);
	
	/* 메일 리스트 */
	public List<MailDTO> getMailList(Map<String, Object> map);
	
	/* 메일 상세 */
	/* public MailDTO getMailByNo(int mailNo); */
	
}
