package com.gdu.workship.service;

import javax.servlet.http.HttpServletRequest;

import org.springframework.ui.Model;

public interface MemberService {
  public void loadMemberList(HttpServletRequest request, Model model);
}
