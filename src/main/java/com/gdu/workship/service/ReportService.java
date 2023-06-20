package com.gdu.workship.service;

import java.util.Map;

import javax.servlet.http.HttpServletRequest;

public interface ReportService {
  public Map<String, Object> loadReportSearchList(HttpServletRequest request);
  public Map<String, Object> loadReportByNo(HttpServletRequest request);
  public Map<String, Object> deleteBoardByReport(HttpServletRequest request);
  public Map<String, Object> returnReport(HttpServletRequest request);
  public Map<String, Object> reportCheck(HttpServletRequest request);
  public Map<String, Object> addReport(HttpServletRequest request);
  
}
