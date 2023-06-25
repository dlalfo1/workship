package com.gdu.workship.service;

import javax.servlet.http.HttpServletRequest;

import org.springframework.ui.Model;
import org.springframework.web.multipart.MultipartHttpServletRequest;

public interface ProjectService {
	public int addProjectM(HttpServletRequest request);
	public int deleteProjectM(int projectNo);
	public int updateProjectM(HttpServletRequest request);
	public int addProjectW(HttpServletRequest request);
	public int deleteProjectW(int projectWorkNo);
	public int updateProjectW(HttpServletRequest request);
	public void projectWDetail(int projectWorkNo, Model model);
	public void getProjectList(HttpServletRequest request, Model model);
	public void getProjectWorkList(HttpServletRequest request, Model model);
}
