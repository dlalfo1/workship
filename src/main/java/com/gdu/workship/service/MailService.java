package com.gdu.workship.service;

import javax.servlet.http.HttpServletRequest;

import org.springframework.ui.Model;

public interface MailService {
	public void getMailRlist(HttpServletRequest request, Model model);
}
