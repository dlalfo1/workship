package com.gdu.workship.controller;

import java.util.Map;

import javax.servlet.http.HttpServletRequest;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.ResponseBody;

import com.gdu.workship.service.ReportService;

import lombok.RequiredArgsConstructor;

@RequiredArgsConstructor
@Controller
public class ReportController {
  
  private final ReportService reportService;
  
  @GetMapping(value="/report/reportSearch.do", produces="application/json")
  @ResponseBody
  public Map<String, Object> searchReport(HttpServletRequest request) {
    return reportService.loadReportSearchList(request);
  }
  
  // 신고 여부 조회
  @GetMapping(value="rpCheck.bo", produces="application/json")
  @ResponseBody
  public Map<String, Object> ajaxCheckReport(HttpServletRequest request) {
    Report rep = bService.checkReport(request);
    
    return new Gson().toJson(rep);
  }
  
  // 신고
  @PostMapping(value="report.bo", produces="application/json")
  @ResponseBody
  public Map<String, Object> ajaxInsertReport(HttpServletRequest request) {
    int result = bService.insertReport(request);
    
    JSONObject jObj = new JSONObject();
    jObj.put("result", result);
    
    return jObj.toJSONString();
  }
  
  @GetMapping(value="/report/reportDetail.do", produces="application/json")
  @ResponseBody
  public Map<String, Object> reportDetail(HttpServletRequest request) {
    return null;
  }
  

}
