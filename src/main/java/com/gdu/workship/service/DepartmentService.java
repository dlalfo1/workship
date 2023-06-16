package com.gdu.workship.service;

import javax.servlet.http.HttpServletRequest;

import org.springframework.ui.Model;

public interface DepartmentService {
  public void loadDepartmentList(HttpServletRequest request, Model model);
  public int addDept(HttpServletRequest request);
  public int removeDept(HttpServletRequest request);
}
