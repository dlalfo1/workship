package com.gdu.workship.service;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

import org.springframework.stereotype.Service;

import com.gdu.workship.domain.MemberDTO;
import com.gdu.workship.mapper.TempMapper;

import lombok.RequiredArgsConstructor;

@RequiredArgsConstructor
@Service
public class TempService {

	private final TempMapper tempMapper;
	
	public void login(HttpServletRequest request, HttpServletResponse response) {
		String url = request.getParameter("url");
		String id = request.getParameter("id");
		String pw = request.getParameter("pw");
		
		MemberDTO memberDTO = new MemberDTO();
		memberDTO.setEmailId(id);
		memberDTO.setPw(pw);
		MemberDTO loginMemberDTO = tempMapper.getMemberDTO(memberDTO);
		
		HttpSession session = request.getSession();
		session.setAttribute("loginMember", loginMemberDTO);
		
		try {
			 response.sendRedirect(url);
		} catch (Exception e) {
			e.printStackTrace();
		}
	}
	
}
