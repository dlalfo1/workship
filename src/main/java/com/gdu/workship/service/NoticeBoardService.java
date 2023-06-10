package com.gdu.workship.service;

import javax.servlet.http.HttpServletRequest;

import org.springframework.ui.Model;

public interface NoticeBoardService {
  public void loadNoticeBoardList(HttpServletRequest request, Model model);
  public void getNoticeByNo(int noticeNo, Model model);
}
