package com.gdu.workship.service;

import java.util.Map;

import javax.servlet.http.HttpServletRequest;

import org.springframework.core.io.Resource;
import org.springframework.http.ResponseEntity;
import org.springframework.ui.Model;
import org.springframework.web.multipart.MultipartHttpServletRequest;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;

public interface MailService {
	public void getMailSideCheck(HttpServletRequest request, Model model);
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
	public Map<String, Object> changeStar(Map<String, Object> map, HttpServletRequest request);
	public Map<String, Object> changeStatus(Map<String, Object> map, HttpServletRequest request);
	public Map<String, Object> mailReadCheck(Map<String, Object> map, HttpServletRequest request);
	public int sendMail(MultipartHttpServletRequest multipartRequest, RedirectAttributes redirectAttributes);
	public ResponseEntity<Resource> attachDownload(int mailFileNo, String userAgent);
	public ResponseEntity<Resource> attachDownloadAll(int mailNo);	
}
