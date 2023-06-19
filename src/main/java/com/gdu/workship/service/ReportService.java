package com.gdu.workship.service;

import java.util.Map;

import javax.servlet.http.HttpServletRequest;

public interface ReportService {
  public Map<String, Object> loadReportSearchList(HttpServletRequest request);
}
