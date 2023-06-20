package com.gdu.workship.service;

import java.util.Map;

import javax.servlet.http.HttpServletRequest;

import org.springframework.ui.Model;
import org.springframework.web.multipart.MultipartHttpServletRequest;

public interface ProjectService {
	public int addProjectM(MultipartHttpServletRequest multipartHttpRequest);
	public int deleteProjectM(int projectNo);
	public int updateProjectM(MultipartHttpServletRequest multipartHttpRequest);
	public int addProjectW(MultipartHttpServletRequest multipartHttpRequest);
	public int deleteProjectW(int projectWorkNo);
	public int updateProjectW(MultipartHttpServletRequest multipartrequest);
	public void projectWDetail(int projectWorkNo, Model model);
	public void getProjectList(HttpServletRequest request, Model model);
	public Map<String, Object> getProjectWorkList(HttpServletRequest request);
}
