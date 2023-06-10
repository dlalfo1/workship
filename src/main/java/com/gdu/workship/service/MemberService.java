package com.gdu.workship.service;

import javax.servlet.http.HttpServletRequest;

import org.springframework.ui.Model;
import org.springframework.web.multipart.MultipartHttpServletRequest;

public interface MemberService {
  public void loadMemberList(HttpServletRequest request, Model model);
  public int addMember(MultipartHttpServletRequest request);
}
