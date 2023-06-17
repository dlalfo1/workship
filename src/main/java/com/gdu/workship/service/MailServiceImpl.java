package com.gdu.workship.service;

import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Optional;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpSession;

import org.springframework.stereotype.Service;
import org.springframework.ui.Model;

import com.gdu.workship.domain.MailDTO;
import com.gdu.workship.domain.MemberDTO;
import com.gdu.workship.mapper.MailMapper;
import com.gdu.workship.util.PageUtil;

import lombok.RequiredArgsConstructor;

@RequiredArgsConstructor
@Service
public class MailServiceImpl implements MailService {

	private final MailMapper mailMapper;
	private final PageUtil pageUtil;	

	@Override
	public void getMailList(HttpServletRequest request, Model model) {
		
		Optional<String> opt1 = Optional.ofNullable(request.getParameter("column"));
		String column = opt1.orElse("");
		
		Optional<String> opt2 = Optional.ofNullable(request.getParameter("query"));
		String query = opt2.orElse("");
		
		String mailCategory = request.getParameter("mailCategory");
		
		HttpSession session = request.getSession();
		MemberDTO loginMemberDTO = (MemberDTO)session.getAttribute("loginMember");
		String emailId = loginMemberDTO.getEmailId();
		
		Map<String, Object> map = new HashMap<String, Object>();
		map.put("column", column);
		map.put("query", query);
		map.put("mailCategory", mailCategory);
		map.put("emailId", emailId);
		
		Optional<String> opt3 = Optional.ofNullable(request.getParameter("page"));
		int page = Integer.parseInt(opt3.orElse("1"));
 
		int totalRecord = mailMapper.getMailCount(map);
		int mailTotalRecord = mailMapper.getMailTotalCount(map);
		int mailNoReadRecord = mailMapper.getMailNoReadCount(map);
		int mailStarRecord = mailMapper.getMailStarCount(map);		
		
		Optional<Object> opt4 = Optional.ofNullable(session.getAttribute("recordPerPage"));
		int recordPerPage = (int)(opt4.orElse(10));
		
		pageUtil.setPageUtil(page, totalRecord, recordPerPage);
		
		map.put("begin", pageUtil.getBegin());
		map.put("end", pageUtil.getEndPage());
		map.put("recordPerPage", recordPerPage);
		
		List<MailDTO> mailList = mailMapper.getMailList(map);
	
		model.addAttribute("totalRecord", totalRecord);
		model.addAttribute("mailList", mailList);
		model.addAttribute("pagination", pageUtil.getPagination(request.getContextPath() + "/mail/list.html?mailCategory=" + mailCategory + "&column=" + column + "&query=" + query));
		model.addAttribute("beginNo", totalRecord - (page - 1) * recordPerPage);
		model.addAttribute("page", page);
		model.addAttribute("mailCategory", mailCategory);
		model.addAttribute("mailTotalRecord", mailTotalRecord);
		model.addAttribute("mailNoReadRecord", mailNoReadRecord);
		model.addAttribute("mailStarRecord", mailStarRecord);		

	}
	/*
	 * @Override public MailDTO getMailByNo(HttpServletRequest request) {
	 * 
	 * Optional<String> opt1 = Optional.ofNullable(request.getParameter("mailNo"));
	 * int mailNo = Integer.parseInt(opt1.orElse("0"));
	 * 
	 * return mailMapper.getMailByNo(mailNo); }
	 */
}
