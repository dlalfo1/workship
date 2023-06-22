package com.gdu.workship.service;

import java.util.Map;

import javax.servlet.http.HttpServletRequest;

import org.springframework.ui.Model;

public interface MailService {
	public void getMailList(HttpServletRequest request, Model model);
	public void getMailListByNo(HttpServletRequest request, Model model);
	public Map<String, Object> updateStar(Map<String, Object> map, HttpServletRequest request);
	public Map<String, Object> updateStatus(Map<String, Object> map, HttpServletRequest request);
	public Map<String, Object> updateToTrash(Map<String, Object> map, HttpServletRequest request);
	public Map<String, Object> updateToSpam(Map<String, Object> map, HttpServletRequest request);
	public Map<String, Object> deleteSentMail(Map<String, Object> map, HttpServletRequest request);
	public Map<String, Object> deleteReceiveMail(Map<String, Object> map, HttpServletRequest request);
	public Map<String, Object> updateSpamCancel(Map<String, Object> map, HttpServletRequest request);
	public Map<String, Object> updateRemoveCancel(Map<String, Object> map, HttpServletRequest request);
}
