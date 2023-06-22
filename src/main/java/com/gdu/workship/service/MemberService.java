package com.gdu.workship.service;

import java.util.Map;

import javax.servlet.http.HttpServletRequest;

import org.springframework.http.ResponseEntity;
import org.springframework.ui.Model;
import org.springframework.web.multipart.MultipartHttpServletRequest;

public interface MemberService {
  public void loadMemberList(HttpServletRequest request, Model model);
  public Map<String, Object> loadMemberList2(HttpServletRequest request);
  public Map<String, Object> loadRetiredMemberList(HttpServletRequest request);
  public Map<String, Object> verifyEmail(String emailId);
  public void loadDeptList(Model model);
  public int addMember(MultipartHttpServletRequest request);
  public void memberDetail(int memberNo, Model model);
  public int modifyMember(MultipartHttpServletRequest request);
  public ResponseEntity<byte[]> display(int memberNo);
  public int removeMember(int memberNo);
}
