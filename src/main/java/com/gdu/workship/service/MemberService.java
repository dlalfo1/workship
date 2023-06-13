package com.gdu.workship.service;

import java.util.Map;

import javax.servlet.http.HttpServletRequest;

import org.springframework.ui.Model;
import org.springframework.web.multipart.MultipartHttpServletRequest;

public interface MemberService {
  public void loadMemberList(HttpServletRequest request, Model model);
  //public Map<String, Object> loadMemberList2(HttpServletRequest request);
  public int addMember(MultipartHttpServletRequest request);
}
