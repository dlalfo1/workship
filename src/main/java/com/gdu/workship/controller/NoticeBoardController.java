package com.gdu.workship.controller;

import javax.servlet.http.HttpServletRequest;

import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestParam;

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
  
  @GetMapping("/notice/noticeDetail.html")
  public String noticeDetail(@RequestParam(value="noticeNo", required=false, defaultValue="0") int noticeNo, Model model) {
    noticeBoardService.getNoticeByNo(noticeNo, model);
    return "notice/noticeDetail";
  }
  
	@GetMapping("/notice/write.html")
	public String write() {
		return "notice/noticeWrite";
	}
	
}
