package com.gdu.workship.service;

import java.io.PrintWriter;
import java.util.Date;

import javax.servlet.http.Cookie;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

import org.springframework.stereotype.Service;

import com.gdu.workship.domain.MemberDTO;
import com.gdu.workship.mapper.LoginMapper;
import com.gdu.workship.mapper.MemberMapper;

import lombok.RequiredArgsConstructor;

@RequiredArgsConstructor
@Service
public class LoginServiceImpl implements LoginService {
	
	private final MemberMapper memberMapper;
	private final LoginMapper loginMapper;
	// private final PageUtil pageUtil;
	// private final MyFileUtil myfileUtil;
	// private final SecurityUtil securityUtil; 

	@Override
	public void login(HttpServletRequest request, HttpServletResponse response) {
		
		String url = request.getParameter("url");
		String emailId = request.getParameter("emailId");
		String pw = request.getParameter("pw");
		// pw = securityUtil.getSha256(pw);
		MemberDTO memberDTO = new MemberDTO();
		memberDTO.setEmailId(emailId);
		memberDTO.setPw(pw);
		
		MemberDTO loginMemberDTO = loginMapper.getMemberDTO(memberDTO);
		if(loginMemberDTO != null) {
			
			autologin(request, response);
				HttpSession session = request.getSession();
				session.setAttribute("loginMember", loginMemberDTO);
				
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
				out.println("alert('일치하는 회원 정보가 없습니다.');");
				out.println("location.href='" + request.getContextPath() + "/index.html';");
				out.println("</script>");
				out.flush();
				out.close();
				
			} catch (Exception e) {
				e.printStackTrace();
			}
		}
		
	}	

	 @Override
	public void logout(HttpServletRequest request, HttpServletResponse response) {
	    HttpSession session = request.getSession();
	    String id = (String) session.getAttribute("loginId");
	    loginMapper.deleteAutologin(id);
	    
	    Cookie cookie = new Cookie("autologinId", "");
	    cookie.setMaxAge(0);                       
	    cookie.setPath("/"); 
	    response.addCookie(cookie);
	    
	    session.invalidate();
	    
		
	}
	 @Override
	public void autologin(HttpServletRequest request, HttpServletResponse response) {
	    String id = request.getParameter("emailId");
	    String chkAutologinId = request.getParameter("chkAutologinId");
	    // 자동 로그인을 체크한 경우
	    if(chkAutologinId != null) {
	      
	      // session의 id를 가져온다.
	      HttpSession session = request.getSession();
	      String sessionId = session.getId();  // sessionId : 브라우저가 새롭게 열릴때마다 자동으로 갱신되는 임의의 값
	      
	      // DB로 보낼 UserDTO 만들기
	      MemberDTO memberDTO = new MemberDTO();
	      memberDTO.setEmailId(id);
	      memberDTO.setAutologinId(sessionId);
	      memberDTO.setSetAutologinExpiredAt(new Date(System.currentTimeMillis() + 1000 * 60 * 60 * 24 * 30));
	                                    // 현재 + 15일 : java.sql.Date 클래스를 이용해서 작업을 수행한다.
	                                    // java.sql.Date 클래스는 타임스탬프를 이용해서 날짜를 생성한다.
	      
	      // DB로 UserDTO 보내기
	      loginMapper.insertAutologin(memberDTO);
	      
	      // session id를 쿠키에 저장하기
	      Cookie cookie = new Cookie("autologinId", sessionId);
	      cookie.setMaxAge(60 * 60 * 24 * 30);      // 초 단위로 15일 지정
	      cookie.setPath("/"); // 애플리케이션의 모든 URL에서 autologinId 쿠키를 확인할 수 있다.
	      response.addCookie(cookie);
	      
	    }
	    // 자동 로그인을 체크하지 않은 경우
	    else {
	      
	      // DB에서 AUTOLOGIN_ID 칼럼과 AUTOLOGIN_EXPIRED_AT 칼럼 정보 삭제하기
	      loginMapper.deleteAutologin(id);
	      
	      // autologinId 쿠키 삭제하기
	      Cookie cookie = new Cookie("autologinId", "");
	      cookie.setMaxAge(0);                       // 쿠키 유지시간을 0초로 설정
	      cookie.setPath("/");  // autologinId 쿠키의 path와 동일하게 설정
	      response.addCookie(cookie); 
	      
	    }

	}


}
