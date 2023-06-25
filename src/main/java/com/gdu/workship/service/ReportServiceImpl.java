package com.gdu.workship.service;

import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Optional;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpSession;

import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.gdu.workship.domain.BoardDTO;
import com.gdu.workship.domain.MemberDTO;
import com.gdu.workship.domain.ReportDTO;
import com.gdu.workship.mapper.ReportMapper;
import com.gdu.workship.util.PageUtil2;

import lombok.RequiredArgsConstructor;

@RequiredArgsConstructor
@Service
public class ReportServiceImpl implements ReportService {

  private final ReportMapper reportMapper;
  private final PageUtil2 pageUtil;
  
  @Override
  public Map<String, Object> loadReportSearchList(HttpServletRequest request) {

    Optional<String> opt1 = Optional.ofNullable(request.getParameter("page"));
    int page = Integer.parseInt(opt1.orElse("1"));
    int totalRecord = reportMapper.getReportCount();
    
    HttpSession session = request.getSession();
    Optional<Object> opt2 = Optional.ofNullable(session.getAttribute("recordPerPage"));
    int recordPerPage = (int)(opt2.orElse(10));
    System.out.println("세션레코드: " + session.getAttribute("recordPerPage"));
    
    Optional<String> opt3 = Optional.ofNullable(request.getParameter("column"));
    String column = opt3.orElse("R.REPORT_CONTENT");
    
    Optional<String> opt4 = Optional.ofNullable(request.getParameter("query"));
    String query = opt4.orElse("");

    Optional<String> opt5 = Optional.ofNullable(request.getParameter("reportState"));
    String reportState = opt5.orElse("");
    
    Map<String, Object> map = new HashMap<String, Object>();
    map.put("column", column);
    map.put("query", query);
    map.put("reportState", reportState);
    // PageUtil(Pagination에 필요한 모든 정보) 계산하기
    map.put("recordPerPage", recordPerPage);    // end 대신 recordPerPage를 전달한다.
    pageUtil.setPageUtil(page, (column.isEmpty() && query.isEmpty()) ? totalRecord : reportMapper.getReportSearchCount(map), recordPerPage);
    map.put("begin", pageUtil.getBegin());      // begin은 0부터 시작한다. (PageUtil.java 참고)
    System.out.println("query: " + query);
    
    /*
     * if(query == "") String rmQuery = "R" + map.get("rmQuery");
     * map.replace("rmQuery", rmQuery)
     */
    List<ReportDTO> reportSearchList = reportMapper.loadReportSearchList(map);
    System.out.println("사이즈 : " + reportSearchList.size());
    BoardDTO board = new BoardDTO();
    for(int i = 0; i < reportSearchList.size(); i++) {
      board = reportMapper.getBoardByReportNo(reportSearchList.get(i).getBoardDTO().getBoardNo());
      reportSearchList.get(i).setBoardDTO(board);
      System.out.println(i + " : " + board);
    }
    
    Map<String, Object> result = new HashMap<String, Object>();
    result.put("reportSearchList", reportSearchList);
    result.put("pageUtil", pageUtil);
    System.out.println(result.get("pageUtil"));
    System.out.println(result);
    System.out.println(pageUtil.getBeginPage());
    System.out.println(pageUtil.getEndPage());
    System.out.println(pageUtil.getTotalPage());
    return result;
  }
  
  @Override
  public Map<String, Object> loadReportByNo(HttpServletRequest request) {
    String strReportNo = request.getParameter("reportNo");
    int reportNo = 0;
    if(strReportNo.isEmpty() == false) reportNo = Integer.parseInt(strReportNo);
    ReportDTO report = reportMapper.loadReportByNo(reportNo);
    System.out.println("report : " + report);
    int reportCount = 0;
    BoardDTO board = report.getBoardDTO();
    if(board != null) reportCount = reportMapper.getReportCountByBoardNo(board.getBoardNo());
    if(board == null) reportCount = 0;
    
    System.out.println("reportCount : " + reportCount);
    Map<String, Object> result = new HashMap<String, Object>();
    result.put("report", report);
    result.put("reportCount", reportCount);
    return result;
  }
  
  @Transactional
  @Override
  public Map<String, Object> deleteBoardByReport(HttpServletRequest request) {
    String strBoardNo = request.getParameter("boardNo");
    int boardNo = 0;
    if(strBoardNo != null && strBoardNo.isEmpty() == false) boardNo = Integer.parseInt(strBoardNo);
    
    Map<String, Object> map = new HashMap<String, Object>();
    List<Integer> reportNoList = reportMapper.getReportByBoardNo(boardNo);
    System.out.println("reportNoList: " + reportNoList);
    ReportDTO report = new ReportDTO();
    for(int reportNo : reportNoList) {
      report = reportMapper.loadReportByNo(reportNo);
      report.setReportState(2);   // 처리완료
      reportMapper.changeState(report);
      System.out.println("reportState : " + report.getReportState());
    }
    
    map.put("isDelete", reportMapper.deleteBoardByReport(boardNo) == 1);
    
    return map;
  }
  
  @Transactional
  @Override
  public Map<String, Object> returnReport(HttpServletRequest request) {
    String strReportNo = request.getParameter("reportNo");
    int reportNo = 0;
    if(strReportNo != null && strReportNo.isEmpty() == false) reportNo = Integer.parseInt(strReportNo);
    
    Map<String, Object> map = new HashMap<String, Object>();
    ReportDTO report = reportMapper.loadReportByNo(reportNo);
    if(report.getReportState() == 2 || report.getReportState() == 1) {
      map.put("isChange", false);
      return map;
    }
    report.setReportState(1);    // 반려
    
    System.out.println(report);
    map.put("isChange", reportMapper.changeState(report) == 1);
    
    return map;
  }
  
  @Override
  public Map<String, Object> reportCheck(HttpServletRequest request) {
    Optional<String> opt1 = Optional.ofNullable(request.getParameter("boardNo"));
    Optional<String> opt2 = Optional.ofNullable(request.getParameter("emailId"));
    int boardNo = Integer.parseInt(opt1.orElse("0"));
    String emailId = opt2.orElse("");
    System.out.println(emailId);
    int memberNo = reportMapper.rGetMemberByEmailId(emailId).getMemberNo();
    Map<String, Object> map = new HashMap<String, Object>();
    map.put("boardNo", boardNo);
    map.put("memberNo", memberNo);
    
    
    Map<String, Object> result = new HashMap<String, Object>();
    result.put("check", reportMapper.reportCheck(map));
    System.out.println("result : " + result);
    return result;
  }
  
  @Override
  public Map<String, Object> addReport(HttpServletRequest request) {
    Optional<String> opt1 = Optional.ofNullable(request.getParameter("boardNo"));
    Optional<String> opt2 = Optional.ofNullable(request.getParameter("emailId"));
    Optional<String> opt3 = Optional.ofNullable(request.getParameter("reportContent"));
    int boardNo = Integer.parseInt(opt1.orElse("0"));
    String emailId = opt2.orElse("");
    String reportContent = opt3.orElse("");
    
    System.out.println(emailId);
    int memberNo = reportMapper.rGetMemberByEmailId(emailId).getMemberNo();
    
    BoardDTO boardDTO = new BoardDTO();
    MemberDTO memberDTO = new MemberDTO();
    boardDTO.setBoardNo(boardNo);
    memberDTO.setMemberNo(memberNo);
    ReportDTO reportDTO = new ReportDTO();
    reportDTO.setBoardDTO(boardDTO);
    reportDTO.setMemberDTO(memberDTO);
    reportDTO.setReportContent(reportContent);
    
    Map<String, Object> result = new HashMap<String, Object>();
    result.put("isAdd", reportMapper.addReport(reportDTO) == 1);
    
    return result;
  }
  
}
