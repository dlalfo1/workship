package com.gdu.workship.mapper;

import java.util.List;
import java.util.Map;

import org.apache.ibatis.annotations.Mapper;

import com.gdu.workship.domain.NoticeDTO;

@Mapper
public interface NoticeBoardMapper {
  public int getNoticeCount();
  public List<NoticeDTO> getNoticeList(Map<String, Object> map);
  public int getNoticeSearchCount(Map<String, Object> map);
  
  public NoticeDTO getNoticeByNo(int noticeNo);
  public NoticeDTO getAttachList(int noticeNo);
}
