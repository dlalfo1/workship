package com.gdu.workship.controller;

import java.util.Map;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpSession;

import org.springframework.core.io.Resource;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestHeader;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.multipart.MultipartHttpServletRequest;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;

import com.gdu.workship.domain.MemberDTO;
import com.gdu.workship.service.NoticeBoardService;

import lombok.RequiredArgsConstructor;

@RequiredArgsConstructor
@Controller
public class NoticeBoardController {

  private final NoticeBoardService noticeBoardService;
  
  @GetMapping("/notice/noticeMain.html")
  public String noticeMain(HttpServletRequest request, Model model) {
    noticeBoardService.loadNoticeBoardList(request, model);
    return "notice/noticeMain";
  }
  /*@GetMapping(value="/notice/noticeList2.do", produces="application/json")
  @ResponseBody
  public Map<String, Object> noticeMain2(HttpServletRequest request) {
    return noticeBoardService.loadNoticeBoardList2(request);
  }*/
  
  @GetMapping("/notice/increaseHit.do")
  public String increseHit(@RequestParam(value="noticeNo", required=false, defaultValue="0") int noticeNo) {
    int increaseResult = noticeBoardService.increaseHit(noticeNo);
    if(increaseResult == 1) {
      return "redirect:/notice/noticeDetail.html?noticeNo=" + noticeNo;
    } else {
      return "redirect:/notice/noticeMain.html";
    }
  }
  
  @GetMapping("/notice/noticeDetail.html")
  public String noticeDetail(@RequestParam(value="noticeNo", required=false, defaultValue="0") int noticeNo, Model model) {
    noticeBoardService.getNoticeByNo(noticeNo, model);
    return "notice/noticeDetail";
  }
  
  @GetMapping("/notice/download.do")
  public ResponseEntity<Resource> download(@RequestParam("noticeFileNo") int noticeFileNo, @RequestHeader("User-Agent") String userAgent) {
    return noticeBoardService.download(noticeFileNo, userAgent);
  }
  
  @GetMapping("/notice/downloadAll.do")
  public ResponseEntity<Resource> downloadAll(@RequestParam("noticeNo") int noticeNo) {
    return noticeBoardService.downloadAll(noticeNo);
  }
  
	@GetMapping("/notice/write.html")
	public String write(HttpSession session, Model model) {
	  MemberDTO member = noticeBoardService.goWrtie(session, model);
	  model.addAttribute("member", member);
		return "notice/noticeWrite";
	}
	
	@PostMapping("/notice/addNotice.do")
	public String addNotice(MultipartHttpServletRequest request, RedirectAttributes redirectAttributes) {
	  int addResult = noticeBoardService.addNotice(request);
    redirectAttributes.addFlashAttribute("addResult", addResult);
	  return "redirect:/notice/noticeMain.html";
	}
	
  @PostMapping("/notice/removeNotice.do")
  public String removeNotice(@RequestParam("noticeNo") int noticeNo, RedirectAttributes redirectAttributes) { 
    int removeResult = noticeBoardService.removeNotice(noticeNo);
    redirectAttributes.addFlashAttribute("removeResult", removeResult);
    return "redirect:/notice/noticeMain.html";
  }
	
  @PostMapping("/notice/editNotice.html")
  public String editNotice(@RequestParam("noticeNo") int noticeNo, Model model) {
    noticeBoardService.getNoticeByNo(noticeNo, model);
    return "notice/noticeEdit";
  }
  
  @PostMapping("/notice/modifyNotice.do")
  public String modifyNotice(MultipartHttpServletRequest request, RedirectAttributes redirectAttributes) {
    int modifyResult = noticeBoardService.modifyNotice(request);
    redirectAttributes.addFlashAttribute("modifyResult", modifyResult);
    return "redirect:/notice/noticeDetail.html?noticeNo=" + request.getParameter("noticeNo");
  }
  
  @GetMapping("/notice/removeNoticeFile.do")
  public String removeNoticeFile(@RequestParam("noticeNo") int noticeNo, @RequestParam("noticeFileNo") int noticeFileNo) {
    noticeBoardService.removeNoticeFile(noticeFileNo);
    return "redirect:/notice/noticeDetail.html?noticeNo=" + noticeNo;
  }
  
  

  // 게시글 임시저장
  @PostMapping(value="/notice/tempSave.do", produces="application/json")
  @ResponseBody
  public Map<String, Object> ajaxSaveBoard(MultipartHttpServletRequest request) {
    return noticeBoardService.tempSave(request);
  }
  
  // 임시저장 리스트 조회
  @GetMapping(value="/notice/saveList.do", produces="application/json")
  @ResponseBody
  public Map<String, Object> ajaxSaveList(HttpServletRequest request) {
    return noticeBoardService.getSaveList(request);
  }
  
  // 임시저장 게시글 조회, 뫄델
  @GetMapping(value="/notice/getSaveByNo.do", produces="application/json")
  @ResponseBody
  public Map<String, Object> getSaveByNo(HttpServletRequest request) {
    return noticeBoardService.getSaveByNo(request);
  } 

  // 임시저장 게시글 삭제
  @PostMapping(value="/notice/deleteSave.do", produces="application/json")
  @ResponseBody
  public Map<String, Object> deleteSave(HttpServletRequest request) {
    return noticeBoardService.deleteSave(request);
  } 
	
}
