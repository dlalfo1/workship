package com.gdu.workship.mapper;

import java.util.List;
import java.util.Map;

import org.apache.ibatis.annotations.Mapper;

import com.gdu.workship.domain.BoardDTO;
import com.gdu.workship.domain.ReportDTO;

@Mapper
public interface ReportMapper {
  // public List<ReportDTO> loadReportList(Map<String, Object> map);
  public int getReportCount();
  public BoardDTO getBoardByReportNo(int boardNo);
  public List<ReportDTO> loadReportSearchList(Map<String, Object> map);
  public int getReportSearchCount(Map<String, Object> map);
}
