package com.gdu.workship.service;

import javax.servlet.http.HttpServletRequest;

import org.springframework.ui.Model;

import com.gdu.workship.domain.MailDTO;

public interface MailService {
	public void getMailList(HttpServletRequest request, Model model);
	/* public MailDTO getMailByNo(HttpServletRequest request); */
}
