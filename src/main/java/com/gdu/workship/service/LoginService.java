package com.gdu.workship.service;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;


public interface LoginService {
	
	public void login(HttpServletRequest request, HttpServletResponse response);
	public void autologin(HttpServletRequest request, HttpServletResponse response);
	public void logout(HttpServletRequest request, HttpServletResponse response);

}