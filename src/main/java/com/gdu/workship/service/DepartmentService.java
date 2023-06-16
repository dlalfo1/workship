package com.gdu.workship.service;

import java.util.Map;

import javax.servlet.http.HttpServletRequest;

import org.springframework.ui.Model;

public interface DepartmentService {
  public void loadDepartmentList(HttpServletRequest request, Model model);
  public int addDept(HttpServletRequest request);
  public Map<String, Object> modifyDept(HttpServletRequest request);
  public int removeDept(HttpServletRequest request);
}
