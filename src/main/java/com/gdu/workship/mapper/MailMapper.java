package com.gdu.workship.mapper;

import java.util.List;
import java.util.Map;

import org.apache.ibatis.annotations.Mapper;

import com.gdu.workship.domain.MailDTO;
import com.gdu.workship.domain.MailFileDTO;
import com.gdu.workship.domain.MailToDTO;
import com.gdu.workship.domain.MemberDTO;

@Mapper
public interface MailMapper {
	
	/* 메일 개수 체크 */
	public int getMailTotalCount(Map<String, Object> map);
	public int getMailNoReadCount(Map<String, Object> map);	
	public int getMailStarCount(Map<String, Object> map);	
	public int getMailCount(Map<String, Object> map);
	
	/* 메일 리스트 */
	public List<MailDTO> getMailList(Map<String, Object> map);
	
	/* 받는사람 정보 확인 */
	public MemberDTO getNameByEmail(Map<String, Object> map);
	
	/* 메일 상세 */
	public MailDTO getMailByMailNo(Map<String, Object> map);
	public List<MailToDTO> getMailByMailToNo(Map<String, Object> map);
	public MailToDTO getMailToMeByMailToNo(Map<String, Object> map);
	public List<MailFileDTO> getMailFileByNo(Map<String, Object> map);
	
	/* 체크박스 내 메일 확인 */
	public List<MailToDTO> getMailToByMailNo(Map<String, Object> map);
	
	/* 메일상태변경 */
	public int updateStar(Map<String, Object> map2);
	public int updateStatus(Map<String, Object> map2);
	public int updateToTrash(Map<String, Object> map2);
	public int updateToSpam(Map<String, Object> map2);
	public int deleteSentMail(Map<String, Object> map);
	public int deleteReceiveMail(Map<String, Object> map2);
	public int updateToInbox(Map<String, Object> map2);
	public int changeStar(Map<String, Object> map);
	public int changeStatus(Map<String, Object> map);
	public int mailReadCheck(Map<String, Object> map);
	
	/* 메일 쓰기 */
	public int sendMail(MailDTO mailDTO);
	public int sendMailTo(Map<String, Object> map1);
	public int sendMailCc(Map<String, Object> map2);
	public int sendMailBcc(Map<String, Object> map3);
	public int addAttach(Map<String, Object> map4);
	
	/* 첨부파일 다운로드 */
	public MailFileDTO getMailFileByMailFileNo(int mailFileNo);
	public List<MailFileDTO> getMailAttachList(int mailNo);
	
	/* 주소록 조회 */
	public List<MemberDTO> getMemberList(Map<String, Object> map);
}
