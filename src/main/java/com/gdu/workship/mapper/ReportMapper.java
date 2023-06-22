package com.gdu.workship.mapper;

import java.util.List;
import java.util.Map;

import org.apache.ibatis.annotations.Mapper;

import com.gdu.workship.domain.BoardDTO;
import com.gdu.workship.domain.MemberDTO;
import com.gdu.workship.domain.ReportDTO;

@Mapper
public interface ReportMapper {
  // public List<ReportDTO> loadReportList(Map<String, Object> map);
  public int getReportCount();
  public BoardDTO getBoardByReportNo(int boardNo);
  public List<ReportDTO> loadReportSearchList(Map<String, Object> map);
  public int getReportSearchCount(Map<String, Object> map);
  public ReportDTO loadReportByNo(int reportNo);
  public int getReportCountByBoardNo(int boardNo);
  public List<Integer> getReportByBoardNo(int boardNo);
  // public ReportDTO getReportByReportNo(int reportNo);
  public int changeState(ReportDTO reportDTO);
  public int deleteBoardByReport(int boardNo);
  public int returnReport(int reportNo);
  public ReportDTO reportCheck(Map<String, Object> map);
  public MemberDTO rGetMemberByEmailId(String emailId);
  public int addReport(ReportDTO reportDTO);
}
