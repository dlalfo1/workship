package com.gdu.workship.mapper;

import java.util.List;
import java.util.Map;

import org.apache.ibatis.annotations.Mapper;

import com.gdu.workship.domain.MemberDTO;
import com.gdu.workship.domain.NoticeDTO;
import com.gdu.workship.domain.NoticeFileDTO;

@Mapper
public interface NoticeBoardMapper {

  public MemberDTO getMemberByEmail(String emailId);
  public int getNoticeCount();
  public List<NoticeDTO> getNoticeList(Map<String, Object> map);
  public int getNoticeSearchCount(Map<String, Object> map);
  
  public int increaseHit(int noticeNo);
  
  public NoticeDTO getNoticeByNo(int noticeNo);
  // 이전글,다음글
  public NoticeDTO getPrevNotice(int noticeNo);
  public NoticeDTO getNextNotice(int noticeNo);
  public List<NoticeFileDTO> getNoticeFileList(int noticeNo);
  
  public NoticeFileDTO getNoticeFileByNo(int noticeFileNo);
  
  public int addNotice(NoticeDTO noticeDTO);
  public int addNoticeFile(NoticeFileDTO noticeFileDTO);
  
  public int removeNotice(int noticeNo);
  
  public int modifyNotice(NoticeDTO noticeDTO);
  public int removeNoticeFile(int noticeFileNo);
  
  public int saveNotice(NoticeDTO noticeDTO);
  public int removeNoticeFileBynoticeNo(int noticeNo);
  public int saveUpdateNotice(NoticeDTO noticeDTO);
  public List<NoticeDTO> getSaveList(int memberNo);
  public int deleteSave(int noticeNo);
  public int addSaveNotice(NoticeDTO noticeDTO);
}
