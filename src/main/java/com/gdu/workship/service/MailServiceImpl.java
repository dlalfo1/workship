package com.gdu.workship.service;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Optional;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpSession;

import org.springframework.stereotype.Service;
import org.springframework.ui.Model;

import com.gdu.workship.domain.MailDTO;
import com.gdu.workship.domain.MailToDTO;
import com.gdu.workship.domain.MemberDTO;
import com.gdu.workship.mapper.MailMapper;
import com.gdu.workship.util.PageUtil2;

import lombok.RequiredArgsConstructor;

@RequiredArgsConstructor
@Service
public class MailServiceImpl implements MailService {

	private final MailMapper mailMapper;
	private final PageUtil2 pageUtil;	

	@Override
	public void getMailList(HttpServletRequest request, Model model) {
		
		Optional<String> opt1 = Optional.ofNullable(request.getParameter("column"));
		String column = opt1.orElse("");
		
		Optional<String> opt2 = Optional.ofNullable(request.getParameter("query"));
		String query = opt2.orElse("");
		
		String Category = request.getParameter("Category");
		
		HttpSession session = request.getSession();
		MemberDTO loginMemberDTO = (MemberDTO)session.getAttribute("loginMember");
		String emailId = loginMemberDTO.getEmailId();
		
		Map<String, Object> map = new HashMap<String, Object>();
		map.put("column", column);
		map.put("query", query);
		map.put("Category", Category);
		map.put("emailId", emailId);
		
		Optional<String> opt3 = Optional.ofNullable(request.getParameter("page"));
		int page = Integer.parseInt(opt3.orElse("1"));
 
		Optional<Object> opt4 = Optional.ofNullable(session.getAttribute("recordPerPage"));
		int recordPerPage = (int)(opt4.orElse(10));
		
		int totalRecord = mailMapper.getMailCount(map);
		int mailTotalRecord = mailMapper.getMailTotalCount(map);
		int mailNoReadRecord = mailMapper.getMailNoReadCount(map);
		int mailStarRecord = mailMapper.getMailStarCount(map);		
		
		pageUtil.setPageUtil(page, totalRecord, recordPerPage);
		
		map.put("begin", pageUtil.getBegin());
		map.put("recordPerPage", recordPerPage);
		
		List<MailDTO> mailList = mailMapper.getMailList(map);
		
		List<MemberDTO> mailToList = new ArrayList<>();
		
		Map<String, Object> mailMap = new HashMap<>();
		
		for(int i = 0; i < mailList.size(); i++) {
			MailDTO mail = mailList.get(i);
			String mailTo = mail.getMailToDTO().getMailTo();
			mailMap.put("mailTo", mailTo);
			MemberDTO member = mailMapper.getNameByEmail(mailMap);
			mailToList.add(member);
		}

		
		model.addAttribute("totalRecord", totalRecord);
		model.addAttribute("mailList", mailList);
		model.addAttribute("mailToList", mailToList);
		model.addAttribute("pagination", pageUtil.getPagination(request.getContextPath() + "/mail/list.html?Category=" + Category + "&column=" + column + "&query=" + query));
		model.addAttribute("beginNo", totalRecord - (page - 1) * recordPerPage);
		model.addAttribute("page", page);
		model.addAttribute("Category", Category);
		model.addAttribute("mailTotalRecord", mailTotalRecord);
		model.addAttribute("mailNoReadRecord", mailNoReadRecord);
		model.addAttribute("mailStarRecord", mailStarRecord);	

	}
	
	@Override 
	public void getMailListByNo(HttpServletRequest request, Model model) {
		
		Optional<Object> opt1 = Optional.ofNullable(request.getParameter("mailNo"));
		int mailNo = Integer.parseInt((String) opt1.orElse("0"));		
		
		Optional<String> opt2 = Optional.ofNullable(request.getParameter("Category"));
		String Category = opt2.orElse("");

		HttpSession session = request.getSession();
		MemberDTO loginMemberDTO = (MemberDTO)session.getAttribute("loginMember");
		String emailId = loginMemberDTO.getEmailId();
		
		Map<String, Object> map = new HashMap<String, Object>();
		
		map.put("mailNo", mailNo);
		map.put("Category", Category);
		map.put("emailId", emailId);
		
		int mailTotalRecord = mailMapper.getMailTotalCount(map);
		int mailNoReadRecord = mailMapper.getMailNoReadCount(map);
		int mailStarRecord = mailMapper.getMailStarCount(map);	
		
		List<MailToDTO> totalMailTo = mailMapper.getMailByMailToNo(map);
		
		List<MemberDTO> mailtoList = new ArrayList<>();
		List<MemberDTO> mailccList = new ArrayList<>();
		List<MemberDTO> mailbccList = new ArrayList<>();
		
		Map<String, Object> mailMap = new HashMap<>();
		
		for(int i = 0; i < totalMailTo.size(); i++) { 
			
			MailToDTO mail = totalMailTo.get(i);
			
			String checkMailTo = mail.getMailTo();
			mailMap.put("mailTo", checkMailTo);
			MemberDTO member = mailMapper.getNameByEmail(mailMap);
			
			String role = mail.getMailToRole();
			switch(role) {
				case "TO" : 
					mailtoList.add(member); 
					break;
				case "CC" : 
					mailccList.add(member);
					break;
				case "BCC" : 
					mailbccList.add(member);
					break;	
			}
		}		
	
		model.addAttribute("sentMail", mailMapper.getMailByMailNo(map));	
		model.addAttribute("mailToMe", mailMapper.getMailToMeByMailToNo(map));
		model.addAttribute("mailtoList", mailtoList);
		model.addAttribute("mailccList", mailccList);
		model.addAttribute("mailbccList", mailbccList);
		model.addAttribute("emailId", emailId);
		model.addAttribute("Category", Category);
		model.addAttribute("mailTotalRecord", mailTotalRecord);
		model.addAttribute("mailNoReadRecord", mailNoReadRecord);
		model.addAttribute("mailStarRecord", mailStarRecord);	

	}
	
	@Override
	public Map<String, Object> updateStar(Map<String, Object> map, HttpServletRequest request) {
	
		HttpSession session = request.getSession(); 
		MemberDTO loginMemberDTO = (MemberDTO)session.getAttribute("loginMember"); 
		String mailTo =	loginMemberDTO.getEmailId();

		map.put("mailTo", mailTo);
		
		List<MailToDTO> mailToList = mailMapper.getMailToByMailNo(map);
		
		Map<String, Object> map2 = new HashMap<String, Object>();
		map2.put("mailTo", mailTo);		
		
		int result = 0;		

		for(int i = 0; i < mailToList.size(); i++) {
			MailToDTO mail = mailToList.get(i);
			int mailToNo = mail.getMailToNo();
			map2.put("mailToNo", mailToNo);
			result += mailMapper.updateStar(map2);		
		}
		
		Map<String, Object> map3 = new HashMap<>();
		map3.put("result", result);
		return map3;
		
	}
	
	@Override 
	public Map<String, Object> updateStatus(Map<String, Object> map, HttpServletRequest request) {
		
		HttpSession session = request.getSession(); 
		MemberDTO loginMemberDTO = (MemberDTO)session.getAttribute("loginMember"); 
		String mailTo =	loginMemberDTO.getEmailId();

		map.put("mailTo", mailTo);
		
		List<MailToDTO> mailToList = mailMapper.getMailToByMailNo(map);
		
		Map<String, Object> map2 = new HashMap<String, Object>();
		map2.put("mailTo", mailTo);		
		
		int result = 0;		

		for(int i = 0; i < mailToList.size(); i++) {
			MailToDTO mail = mailToList.get(i);
			int mailToNo = mail.getMailToNo();
			map2.put("mailToNo", mailToNo);
			result += mailMapper.updateStatus(map2);		
		}
		
		Map<String, Object> map3 = new HashMap<>();
		map3.put("result", result);
		return map3;
	}
	
	@Override
	public Map<String, Object> updateToTrash(Map<String, Object> map, HttpServletRequest request) {
		
		HttpSession session = request.getSession(); 
		MemberDTO loginMemberDTO = (MemberDTO)session.getAttribute("loginMember"); 
		String mailTo =	loginMemberDTO.getEmailId();

		map.put("mailTo", mailTo);
		
		List<MailToDTO> mailToList = mailMapper.getMailToByMailNo(map);
		
		Map<String, Object> map2 = new HashMap<String, Object>();
		map2.put("mailTo", mailTo);		
		
		int result = 0;		

		for(int i = 0; i < mailToList.size(); i++) {
			MailToDTO mail = mailToList.get(i);
			int mailToNo = mail.getMailToNo();
			map2.put("mailToNo", mailToNo);
			result += mailMapper.updateToTrash(map2);		
		}
		
		Map<String, Object> map3 = new HashMap<>();
		map3.put("result", result);
		return map3;
		
	}
	
	@Override
	public Map<String, Object> updateToSpam(Map<String, Object> map, HttpServletRequest request) {
		
		HttpSession session = request.getSession(); 
		MemberDTO loginMemberDTO = (MemberDTO)session.getAttribute("loginMember"); 
		String mailTo =	loginMemberDTO.getEmailId();

		map.put("mailTo", mailTo);
		
		List<MailToDTO> mailToList = mailMapper.getMailToByMailNo(map);
		
		Map<String, Object> map2 = new HashMap<String, Object>();
		map2.put("mailTo", mailTo);		
		
		int result = 0;		

		for(int i = 0; i < mailToList.size(); i++) {
			MailToDTO mail = mailToList.get(i);
			int mailToNo = mail.getMailToNo();
			map2.put("mailToNo", mailToNo);
			result += mailMapper.updateToSpam(map2);		
		}
		
		Map<String, Object> map3 = new HashMap<>();
		map3.put("result", result);
		return map3;
	}
	
	@Override
	public Map<String, Object> deleteSentMail(Map<String, Object> map, HttpServletRequest request) {
		HttpSession session = request.getSession(); 
		MemberDTO loginMemberDTO = (MemberDTO)session.getAttribute("loginMember"); 
		String emailId = loginMemberDTO.getEmailId();

		map.put("emailId", emailId);
		
		int result = 0;
		
		result += mailMapper.deleteSentMail(map);
		
		Map<String, Object> map2 = new HashMap<>();
		map2.put("result", result);
		return map2;
	}
	
	@Override
	public Map<String, Object> deleteReceiveMail(Map<String, Object> map, HttpServletRequest request) {
		
		HttpSession session = request.getSession(); 
		MemberDTO loginMemberDTO = (MemberDTO)session.getAttribute("loginMember"); 
		String mailTo =	loginMemberDTO.getEmailId();

		map.put("mailTo", mailTo);
		
		List<MailToDTO> mailToList = mailMapper.getMailToByMailNo(map);
		
		Map<String, Object> map2 = new HashMap<String, Object>();
		map2.put("mailTo", mailTo);		
		
		int result = 0;		

		for(int i = 0; i < mailToList.size(); i++) {
			MailToDTO mail = mailToList.get(i);
			int mailToNo = mail.getMailToNo();
			map2.put("mailToNo", mailToNo);
			result += mailMapper.deleteReceiveMail(map2);		
		}
		
		Map<String, Object> map3 = new HashMap<>();
		map3.put("result", result);
		return map3;
	}
	
	@Override
	public Map<String, Object> updateSpamCancel(Map<String, Object> map, HttpServletRequest request) {
		
		HttpSession session = request.getSession(); 
		MemberDTO loginMemberDTO = (MemberDTO)session.getAttribute("loginMember"); 
		String mailTo =	loginMemberDTO.getEmailId();

		map.put("mailTo", mailTo);
		
		List<MailToDTO> mailToList = mailMapper.getMailToByMailNo(map);
		
		Map<String, Object> map2 = new HashMap<String, Object>();
		map2.put("mailTo", mailTo);		
		
		int result = 0;		

		for(int i = 0; i < mailToList.size(); i++) {
			MailToDTO mail = mailToList.get(i);
			int mailToNo = mail.getMailToNo();
			map2.put("mailToNo", mailToNo);
			result += mailMapper.updateToInbox(map2);		
		}
		
		Map<String, Object> map3 = new HashMap<>();
		map3.put("result", result);
		return map3;
	}
	
	@Override
	public Map<String, Object> updateRemoveCancel(Map<String, Object> map, HttpServletRequest request) {
		
		HttpSession session = request.getSession(); 
		MemberDTO loginMemberDTO = (MemberDTO)session.getAttribute("loginMember"); 
		String mailTo =	loginMemberDTO.getEmailId();

		map.put("mailTo", mailTo);
		
		List<MailToDTO> mailToList = mailMapper.getMailToByMailNo(map);
		
		Map<String, Object> map2 = new HashMap<String, Object>();
		map2.put("mailTo", mailTo);		
		
		int result = 0;		

		for(int i = 0; i < mailToList.size(); i++) {
			MailToDTO mail = mailToList.get(i);
			int mailToNo = mail.getMailToNo();
			map2.put("mailToNo", mailToNo);
			result += mailMapper.updateToInbox(map2);		
		}
		
		Map<String, Object> map3 = new HashMap<>();
		map3.put("result", result);
		return map3;
	}
}
