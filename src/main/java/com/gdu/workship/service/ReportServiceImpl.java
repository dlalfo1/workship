package com.gdu.workship.service;

import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Optional;

import javax.servlet.http.HttpServletRequest;

import org.springframework.stereotype.Service;

import com.gdu.workship.domain.BoardDTO;
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
    
    int recordPerPage = 5;
    
    Optional<String> opt2 = Optional.ofNullable(request.getParameter("column"));
    String column = opt2.orElse("R.REPORT_CONTENT");
    
    Optional<String> opt3 = Optional.ofNullable(request.getParameter("query"));
    String query = opt3.orElse("");

    Optional<String> opt4 = Optional.ofNullable(request.getParameter("reportState"));
    String reportState = opt4.orElse("");
    
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
  
}
