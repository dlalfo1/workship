package com.gdu.workship.service;

import java.io.BufferedInputStream;
import java.io.File;
import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.net.URLEncoder;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Optional;
import java.util.zip.ZipEntry;
import java.util.zip.ZipOutputStream;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpSession;

import org.springframework.core.io.FileSystemResource;
import org.springframework.core.io.Resource;
import org.springframework.http.ContentDisposition;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;
import org.springframework.ui.Model;
import org.springframework.web.multipart.MultipartFile;
import org.springframework.web.multipart.MultipartHttpServletRequest;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;

import com.gdu.workship.domain.MailDTO;
import com.gdu.workship.domain.MailFileDTO;
import com.gdu.workship.domain.MailToDTO;
import com.gdu.workship.domain.MemberDTO;
import com.gdu.workship.mapper.MailMapper;
import com.gdu.workship.util.MyFileUtil;
import com.gdu.workship.util.PageUtil2;

import lombok.RequiredArgsConstructor;

@RequiredArgsConstructor
@Service
public class MailServiceImpl implements MailService {

	private final MailMapper mailMapper;
	private final PageUtil2 pageUtil;	
	private final MyFileUtil myFileUtil;
	
	@Override
	public void getMailSideCheck(HttpServletRequest request, Model model) {
	
		HttpSession session = request.getSession();
		MemberDTO loginMemberDTO = (MemberDTO)session.getAttribute("loginMember");
		String emailId = loginMemberDTO.getEmailId();
		
		Map<String, Object> map = new HashMap<String, Object>();
		map.put("emailId", emailId);
		
		int mailNoReadRecord = mailMapper.getMailNoReadCount(map);
		int mailStarRecord = mailMapper.getMailStarCount(map);	
		
		model.addAttribute("mailNoReadRecord", mailNoReadRecord);
		model.addAttribute("mailStarRecord", mailStarRecord);			
	}

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
		
		boolean hasCcNullValue = false;
		boolean hasBccNullValue = false;
		
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
					if(member == null) {
						hasCcNullValue = true;
					}
					break;
				case "BCC" : 
					mailbccList.add(member);
					if(member == null) {
						hasBccNullValue = true;
					}
					break;	
			}
		}		
		
		List<MailFileDTO> attachList = mailMapper.getMailFileByNo(map);

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
		model.addAttribute("attachList", attachList);		
		model.addAttribute("hasCcNullValue", hasCcNullValue);
		model.addAttribute("hasBccNullValue", hasBccNullValue);
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
	
	@Override
	public Map<String, Object> changeStar(Map<String, Object> map, HttpServletRequest request) {
		
		HttpSession session = request.getSession(); 
		MemberDTO loginMemberDTO = (MemberDTO)session.getAttribute("loginMember"); 
		String emailId = loginMemberDTO.getEmailId();

		map.put("emailId", emailId);
		
		MailToDTO mail = mailMapper.getMailToMeByMailToNo(map);
		
		int mailToNo = mail.getMailToNo();
		map.put("mailToNo", mailToNo);
		
		int result = mailMapper.changeStar(map);
	
		Map<String, Object> map2 = new HashMap<>();
		map2.put("result", result);		
		return map2;
	}
	
	@Override
	public Map<String, Object> changeStatus(Map<String, Object> map, HttpServletRequest request) {

		HttpSession session = request.getSession(); 
		MemberDTO loginMemberDTO = (MemberDTO)session.getAttribute("loginMember"); 
		String emailId = loginMemberDTO.getEmailId();

		map.put("emailId", emailId);
		
		MailToDTO mail = mailMapper.getMailToMeByMailToNo(map);
		
		int mailToNo = mail.getMailToNo();
		map.put("mailToNo", mailToNo);
		
		int result = mailMapper.changeStatus(map);
	
		Map<String, Object> map2 = new HashMap<>();
		map2.put("result", result);		
		return map2;
	}
	
	@Override
	public Map<String, Object> mailReadCheck(Map<String, Object> map, HttpServletRequest request) {

		HttpSession session = request.getSession(); 
		MemberDTO loginMemberDTO = (MemberDTO)session.getAttribute("loginMember"); 
		String emailId = loginMemberDTO.getEmailId();

		map.put("emailId", emailId);
		
		MailToDTO mail = mailMapper.getMailToMeByMailToNo(map);	
		
		int mailToNo = mail.getMailToNo();
		map.put("mailToNo", mailToNo);
		
		int result = mailMapper.mailReadCheck(map);		
		
		Map<String, Object> map2 = new HashMap<>();
		map2.put("result", result);		
		
		return map2;
	}
	
	@Override
	public int sendMail(MultipartHttpServletRequest multipartRequest, RedirectAttributes redirectAttributes) {
		
		HttpSession session = multipartRequest.getSession();
		MemberDTO loginMemberDTO =(MemberDTO)session.getAttribute("loginMember");
		String emailId = loginMemberDTO.getEmailId();
	
		String title = multipartRequest.getParameter("title");
		String content = multipartRequest.getParameter("content");
		
		List<MultipartFile> files = multipartRequest.getFiles("files");
		String mailHasFile = null;
		for(MultipartFile multipartFile : files) {
			
			if(multipartFile != null && multipartFile.isEmpty() == false) {
				mailHasFile = "Y";
				break;
			} else {
				mailHasFile = "N";
			}
		}
		/*
		 * String mailHasFile = (files != null && files.isEmpty() == false) ? "Y" : "N";
		 */
		
		MailDTO mailDTO = new MailDTO();
		MemberDTO memberDTO = new MemberDTO();
		memberDTO.setEmailId(emailId);
		
		mailDTO.setMemberDTO(memberDTO);
		mailDTO.setMailTitle(title);
		mailDTO.setMailContent(content);
		mailDTO.setMailHasFile(mailHasFile);
		
		int sentResult = mailMapper.sendMail(mailDTO);		
				
		String receiver = multipartRequest.getParameter("receiver");
		String[] mailToList = receiver != null ? receiver.split(",") : new String[0];

		int mailToResult = 0;		

		Map<String, Object> map1 = new HashMap<String, Object>();
				
		for(int i = 0; i < mailToList.length; i++) {
			map1.put("mailNo", mailDTO.getMailNo());
			map1.put("mailTo", mailToList[i]);
			mailToResult += mailMapper.sendMailTo(map1);					

		}		
		
		String mailRef = multipartRequest.getParameter("mailRef");
		String[] mailCcList = mailRef != null ? mailRef.split(",") : new String[0];
		
		int mailCcResult = 0;		
		
		Map<String, Object> map2 = new HashMap<String, Object>();
				
		for(int i = 0; i < mailCcList.length; i++) {
			map2.put("mailNo", mailDTO.getMailNo());
			map2.put("mailTo", mailCcList[i]);
			mailCcResult += mailMapper.sendMailCc(map2);					

		}			

		String mailBlind = multipartRequest.getParameter("mailBlind");
		String[] mailBccList = mailBlind != null ? mailBlind.split(",") : new String[0];
		
		int mailBccResult = 0;		
		
		Map<String, Object> map3 = new HashMap<String, Object>();
				
		for(int i = 0; i < mailBccList.length; i++) {
			map3.put("mailNo", mailDTO.getMailNo());
			map3.put("mailTo", mailBccList[i]);
			mailBccResult += mailMapper.sendMailBcc(map3);	
		}		

		
		int mailFileResult = 0;
		Map<String, Object> map4 = new HashMap<String, Object>();
		
		for(MultipartFile multipartFile : files) {
			
			if(multipartFile != null && multipartFile.isEmpty() == false) {
				
				try {
					
					String mailFilePath = myFileUtil.getPath();
					
					File dir = new File(mailFilePath);
					if(dir.exists() == false) {
						dir.mkdirs();
					}
					
					String mailFileOriginName = multipartFile.getOriginalFilename();
					mailFileOriginName = mailFileOriginName.substring(mailFileOriginName.lastIndexOf("\\") + 1);
					
					String mailFileSystemName = myFileUtil.getFilesystemName(mailFileOriginName);
					
					File file = new File(dir, mailFileSystemName);
					
					multipartFile.transferTo(file);
			
					map4.put("mailFileOriginName", mailFileOriginName);
					map4.put("mailFileSystemName", mailFileSystemName);
					map4.put("mailFilePath", mailFilePath);
					map4.put("mailNo", mailDTO.getMailNo());

					mailFileResult += mailMapper.addAttach(map4);

				} catch(Exception e) {
					e.printStackTrace();
				}
			}
		}			
		return sentResult + mailToResult + mailCcResult + mailBccResult + mailFileResult;		
	}
	
	@Override
	public ResponseEntity<Resource> attachDownload(int mailFileNo, String userAgent) {

		MailFileDTO mailFileDTO = mailMapper.getMailFileByMailFileNo(mailFileNo);
		
		File file = new File(mailFileDTO.getMailFilePath(), mailFileDTO.getMailFileSystemName());
		
		Resource resource = new FileSystemResource(file);
		
		if(resource.exists() == false) {
			return new ResponseEntity<Resource>(HttpStatus.NOT_FOUND);
		}
		
		String originName = mailFileDTO.getMailFileOriginName();
		try {
			if(userAgent.contains("Trident")) {
				originName = URLEncoder.encode(originName, "UTF-8").replace("+", " ");
			} else if(userAgent.contains("Edg")) {
		        originName = URLEncoder.encode(originName, "UTF-8");
		    } else {
		        originName = new String(originName.getBytes("UTF-8"), "ISO-8859-1");
		    }
		} catch(Exception e) {
		      e.printStackTrace();
	    }
		
		HttpHeaders responseHeader = new HttpHeaders();
		responseHeader.setContentType(MediaType.APPLICATION_OCTET_STREAM);
		responseHeader.setContentDisposition(ContentDisposition.attachment().filename(originName).build());
		responseHeader.setContentLength(file.length());
		
		return new ResponseEntity<Resource>(resource, responseHeader, HttpStatus.OK);
	
	}
	
	@Override
	public ResponseEntity<Resource> attachDownloadAll(int mailNo) {
		
		String tempPath = myFileUtil.getTempPath();
		File dir = new File(tempPath);
		if(dir.exists() == false) {
			dir.mkdirs();
		}
		
		String tempfileName = myFileUtil.getTempFileName();
		
		File zfile = new File(tempPath, tempfileName);
		
		BufferedInputStream bin = null;
		ZipOutputStream zout = null;
		
		List<MailFileDTO> attachList = mailMapper.getMailAttachList(mailNo);
		
		try {
			
			zout = new ZipOutputStream(new FileOutputStream(zfile));
			
			for(MailFileDTO mailFileDTO : attachList) {
				
				ZipEntry zipEntry = new ZipEntry(mailFileDTO.getMailFileOriginName());
				zout.putNextEntry(zipEntry);
				
				bin = new BufferedInputStream(new FileInputStream(new File(mailFileDTO.getMailFilePath(), mailFileDTO.getMailFileSystemName())));
				
				byte[] b = new byte[1024];
				int readByte = 0;
				while((readByte = bin.read(b)) != -1) {
					zout.write(b, 0, readByte);
				}
				
				bin.close();
				zout.closeEntry();
			}
			
			zout.close();
			
		} catch(Exception e) {
			e.printStackTrace();
		}
		
		Resource resource = new FileSystemResource(zfile);
		
		HttpHeaders responseHeader = new HttpHeaders();
		responseHeader.setContentType(MediaType.APPLICATION_OCTET_STREAM);
		responseHeader.setContentDisposition(ContentDisposition.attachment().filename(tempfileName).build());
		responseHeader.setContentLength(zfile.length());
		
		return new ResponseEntity<Resource>(resource, responseHeader, HttpStatus.OK);
		
	}
	
}
