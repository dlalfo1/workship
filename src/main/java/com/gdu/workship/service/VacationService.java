package com.gdu.workship.service;

import java.util.Map;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpSession;

import org.springframework.ui.Model;

public interface VacationService {
	
	public void getPersonalVacationInfo(HttpSession session, Model model);
	public void getApprovalList(HttpServletRequest request, Model model);
	public Map<String, Object> updateApproval(HttpServletRequest request);
	public void getVacationList(HttpServletRequest request, Model model);
	public Map<String, Object> modifyVacation(HttpServletRequest request);
	
}
