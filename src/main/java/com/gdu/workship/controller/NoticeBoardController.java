package com.gdu.workship.controller;

import java.util.Map;

import javax.servlet.http.HttpServletRequest;

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

import com.gdu.workship.service.NoticeBoardService;

import lombok.RequiredArgsConstructor;

@RequiredArgsConstructor
@Controller
public class NoticeBoardController {

  private final NoticeBoardService noticeBoardService;
  
  @GetMapping("/notice/noticeList.do")
  public String noticeMain(HttpServletRequest request, Model model) {
    noticeBoardService.loadNoticeBoardList(request, model);
    return "notice/noticeMain";
  }
  @GetMapping(value="/notice/noticeList2.do", produces="application/json")
  @ResponseBody
  public Map<String, Object> noticeMain2(HttpServletRequest request) {
    return noticeBoardService.loadNoticeBoardList2(request);
  }
  
  @GetMapping("/notice/increaseHit.do")
  public String increseHit(@RequestParam(value="noticeNo", required=false, defaultValue="0") int noticeNo) {
    int increaseResult = noticeBoardService.increaseHit(noticeNo);
    if(increaseResult == 1) {
      return "redirect:/notice/noticeDetail.html?noticeNo=" + noticeNo;
    } else {
      return "redirect:/notice/noticeList2.do";
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
	public String write() {
		return "notice/noticeWrite";
	}
	
	@PostMapping("/notice/addNotice.do")
	public String addNotice(MultipartHttpServletRequest request, RedirectAttributes redirectAttributes) {
	  int addResult = noticeBoardService.addNotice(request);
    redirectAttributes.addFlashAttribute("addResult", addResult);
	  return "redirect:/notice/noticeList.do";
	}
	
  @PostMapping("/notice/removeNotice.do")
  public String removeNotice(@RequestParam("noticeNo") int noticeNo, RedirectAttributes redirectAttributes) { 
    int removeResult = noticeBoardService.removeNotice(noticeNo);
    redirectAttributes.addFlashAttribute("removeResult", removeResult);
    return "redirect:/notice/noticeList.do";
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
	
}
