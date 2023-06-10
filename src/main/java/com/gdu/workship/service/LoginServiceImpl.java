package com.gdu.workship.service;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import lombok.AllArgsConstructor;
import lombok.Data;

@Data
@AllArgsConstructor
public class LoginServiceImpl implements LoginService {
	
	@Override
	public void login(HttpServletRequest request, HttpServletResponse response) {
		
		String url = request.getParameter("url");
		String id = request.getParameter("emailId");
		String pw = request.getParameter("pw");
		
		pw = securityUtil.
	}	

}
