package com.gdu.workship.service;

import java.util.Map;

import javax.servlet.http.HttpServletRequest;

import org.springframework.ui.Model;

public interface AttendManageService {

	public void updateAllScehduler();
	
	public void getAttendManagePage(HttpServletRequest request, Model model);
	public Map<String, Object> manageSearch(HttpServletRequest request);
}
