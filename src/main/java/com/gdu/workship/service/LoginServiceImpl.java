package com.gdu.workship.service;

import java.io.PrintWriter;

import javax.servlet.http.Cookie;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

import org.springframework.beans.factory.annotation.Autowired;

import com.gdu.workship.domain.MemberDTO;
import com.gdu.workship.mapper.LoginMapper;
import com.gdu.workship.mapper.MemberMapper;
import com.gdu.workship.util.MyFileUtil;
import com.gdu.workship.util.PageUtil;
import com.gdu.workship.util.SecurityUtil;

import lombok.AllArgsConstructor;
import lombok.Data;

@Data
@AllArgsConstructor
public class LoginServiceImpl implements LoginService {
	
	MemberMapper memberMapper;
	LoginMapper loginMapper;
	private PageUtil pageUtil;
	private MyFileUtil myfileUtil;
	private SecurityUtil securityUtil; 
	
	@Override
	public void login(HttpServletRequest request, HttpServletResponse response) {
		
		String url = request.getParameter("url");
		String emailId = request.getParameter("emailId");
		String pw = request.getParameter("pw");
		
		pw = securityUtil.getSha256(pw);
		
		MemberDTO memberDTO = new MemberDTO();
		memberDTO.setEmailId(emailId);
		memberDTO.setPw(pw);
		
		MemberDTO loginMemberDTO = loginMapper.slectMemberByMemberDTO(memberDTO);
		
		if(loginMemberDTO != null) {
			
			autologin(request, response);
			
			HttpSession session = request.getSession();
			session.setAttribute("emailId", emailId);
			
			int updateResult = loginMapper.updateMemberAccess(emailId);
			if(updateResult == 0) {
				loginMapper.insertMemberAccess(emailId);
			}
			
			try {
				response.sendRedirect(url);
			} catch (Exception e) {
				e.printStackTrace();
			}
		}
		else {
			
			try {
				response.setContentType("text/html; charset=UTF-8");
				PrintWriter out = response.getWriter();
				out.println("<script>");
				out.println("alert('일치하는 회원 정보가 없습니다.);");
				out.println("location.href='" + request.getContextPath() + "/loginTemp.html';");
				out.println("</script>");
				out.flush();
				out.close();
				
			} catch (Exception e) {
				e.printStackTrace();
			}
		}
	}	
	 @Autowired
	  public void autologin(HttpServletRequest request, HttpServletResponse response) {
		 String emailId = request.getParameter("emailId");
		 String chkAutoId = request.getParameter("chkAutoId");
		 
		 if(chkAutoId != null) {
			 HttpSession session = request.getSession();
			 String sessionId = session.getId();
			 MemberDTO memberDTO = new MemberDTO();
			 /*
			 memberDTO.setEmailId(emailId);
			 memberDTO.setAutologinId(sessionId);
			 memberDTO.setSetAutologinExpiredAt(new Date(System.currentTimeMillis() + 1000 * 60 * 60 * 24 * 30));
			*/ 
			 loginMapper.insertAutologin(memberDTO);
			 
			 Cookie cookie = new Cookie("autologinId", sessionId);
			 cookie.setMaxAge(60 * 60 * 24 * 30);
			 cookie.setPath(request.getContextPath());
			 response.addCookie(cookie);
		 }
		 else { 
			 loginMapper.deleteAutologin(emailId);
			 
		     Cookie cookie = new Cookie("autologinId", "");
		     cookie.setMaxAge(0);                       
		     cookie.setPath(request.getContextPath());
		     response.addCookie(cookie);
		 }
	 }
	 @Override
	public void logout(HttpServletRequest request, HttpServletResponse response) {
		// TODO Auto-generated method stub
		
	}


}
