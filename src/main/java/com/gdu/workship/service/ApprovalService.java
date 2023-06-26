package com.gdu.workship.service;

import java.util.List;
import java.util.Map;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpSession;

import org.springframework.core.io.Resource;
import org.springframework.http.ResponseEntity;
import org.springframework.ui.Model;
import org.springframework.web.multipart.MultipartHttpServletRequest;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;

import com.gdu.workship.domain.ApprovalDTO;
import com.gdu.workship.domain.ApprovalFileDTO;
import com.gdu.workship.domain.ApprovalLineDTO;
import com.gdu.workship.domain.DepartmentDTO;

public interface ApprovalService {
  
  public List<DepartmentDTO> getDeptList();   // 부서리스트 가져오기
  public Map<String, Object> getMemberList(HttpServletRequest request); // 멤버리스트 가져오기
  public Map<String, Object> getInsertCheckMemberList(Map<String, Object> map); 
  public Map<String, Object> getApprovalAndReferenceMemSberList(Map<String, Object> map);
  public ApprovalDTO detailApprovalByNo(HttpServletRequest request); // 결재문서 상세보기
  public List<ApprovalLineDTO> getApprovalLine(HttpServletRequest request); // 결재문서 상세보기시 결재라인 가져오기
  public List<ApprovalFileDTO> detailApprovalFilesByNo(HttpServletRequest request);  // 첨부파일 가져오기
  public ResponseEntity<Resource> download(int attachNo, String userAgent);  // 첨부파일 다운로드
  public int addApproval(MultipartHttpServletRequest multipartRequest); // 결재 상신
  public int addTemporaryDoc(MultipartHttpServletRequest multipartRequest); // 임시저장
  public int approvalOfDoc(HttpServletRequest request); // 결재하기
  public int rejectOfDoc(HttpServletRequest request); // 반려하기
  public int retrieveApproval(HttpServletRequest request); // 문서회수
  public int removeApproval(HttpServletRequest request); // 삭제하기
  public void getApprovalList(HttpServletRequest request, Model model, HttpSession session); // 결재문서 가져오기
  public void getReferenceList(HttpServletRequest request, Model model, HttpSession session); // 참조문서 가져오기
  public void getRetrieveList(HttpServletRequest request, Model model, HttpSession session); // 회수문서 가져오기
  public Map<String, Object> getAutoComplete(HttpServletRequest request, HttpSession session); // 결재문서 자동완성
  public Map<String, Object> getReferenceAutoComplete(HttpServletRequest request, HttpSession session); // 참조문서 자동완성
  public Map<String, Object> getRetrieveAutoComplete(HttpServletRequest request, HttpSession session); // 회수문서 자동완성

}
