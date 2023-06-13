package com.gdu.workship.service;

import java.util.Map;

import javax.servlet.http.HttpSession;

import org.springframework.ui.Model;

public interface MainService {

	public void main(HttpSession session, Model model);
	public Map<String, Object> aStart(int memberNo);
	public Map<String, Object> aEnd(int memberNo);
	
}
