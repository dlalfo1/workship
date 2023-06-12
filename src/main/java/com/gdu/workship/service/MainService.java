package com.gdu.workship.service;

import java.util.Map;

import javax.servlet.http.HttpSession;

import org.springframework.ui.Model;

public interface MainService {

	public void main(HttpSession session, Model model);
	public Map<String, Object> astart(int memberNo);
	
}
