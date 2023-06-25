package com.gdu.workship.controller;

import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.HashMap;
import java.util.Map;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpSession;

import org.springframework.core.io.Resource;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestHeader;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.multipart.MultipartHttpServletRequest;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;

import com.gdu.workship.service.ApprovalService;

import lombok.RequiredArgsConstructor;

@RequiredArgsConstructor
@RequestMapping("/approval")
@Controller
public class ApprovalController {
  
   private final ApprovalService approvalService;
  
  // 선택 문서 작성화면으로 넘어가기
  @GetMapping("/documentList.html")
  public String documentList(String formNo, HttpSession session, Model model) {
    
    Date date = new Date(System.currentTimeMillis()); // 오늘날짜 구하기. (입사날자, 작성일자)
    SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd");
    String today = sdf.format(date);
    
    model.addAttribute("createdAt", today);
    model.addAttribute("formNo", formNo);
    
    String result = "";
    switch(formNo) {
      case "1":  // 휴가신청서 이동
        result = "approval/vacationDoc";
        break;
      case "2": // 지불결의서 이동
        result = "approval/expenseDoc";
        break;
      case "3": // 업무협조전 이동
        result = "approval/businessDoc";
        break;
      case "4": // 사직서 이동
        result = "approval/resignationDoc";
        break;
    }
    
    return result;
 
  }
  
  // 모달창 부서리스트 가져오기
  @ResponseBody 
  @GetMapping(value="/deptList.do", produces="application/json")
  public Map<String, Object> deptList(){
    
    Map<String, Object> map = new HashMap<String, Object>();
    map.put("deptList", approvalService.getDeptList());
    
    return map;
    
  } 
  
  // 모달창 멤버리스트 가져오기
  @ResponseBody
  @GetMapping(value="/memberList.do", produces="application/json")
  public Map<String, Object> memberList (HttpServletRequest request) {
    
    return approvalService.getMemberList(request);
    
  }
  
  // 모달창 결재라인 체크하기 
  // 배열을 컨트롤러에서 받기.(LIST, MAP, JSON파싱 등)
  @ResponseBody
  @PostMapping(value="/insertCheckApproval.do", produces="application/json")
  public Map<String, Object> insertCheckApproval(@RequestBody Map<String, Object> map) {
    
    return approvalService.getInsertCheckMemberList(map);
    
  }
  
  // 최종 결재라인, 참조라인 등록하기
  @ResponseBody
  @PostMapping(value="/approvalAndReferenceLine.do", produces="application/json")
  public Map<String, Object> approvalAndReferenceLine(@RequestBody Map<String, Object> map) {
    System.out.println(map);
    
    return approvalService.getApprovalAndReferenceMemSberList(map);
  }
  
  // 결재 상신
  @PostMapping("/addApproval.do") // approvalMemberNo, referenceMemberNo, approvalOrder
  public String addApproval(MultipartHttpServletRequest multipartRequest, RedirectAttributes redirectAttributes ) {

    int addResult = approvalService.addApproval(multipartRequest);
    redirectAttributes.addFlashAttribute("addResult", addResult);
    return "redirect:/approval/approvalList.do";
    
  }
  
  // 임시문서
  @PostMapping("/temporaryDoc.do") // approvalMemberNo, referenceMemberNo, approvalOrder
  public String temporaryDoc(MultipartHttpServletRequest multipartRequest, RedirectAttributes redirectAttributes ) {
    
    int addResult = approvalService.addTemporaryDoc(multipartRequest);
    redirectAttributes.addFlashAttribute("addResult", addResult);
    return "redirect:/approval/approvalList.do";
    
  }
  
  // 결재문서 상세보기
  @GetMapping("/detailApproval.do")
  public String detailApproval(HttpServletRequest request, Model model) {
    
    model.addAttribute("approval", approvalService.detailApprovalByNo(request));
    model.addAttribute("attachList", approvalService.detailApprovalFilesByNo(request));
    model.addAttribute("approvalLines", approvalService.getApprovalLine(request));
    return "approval/detailApproval";
    
  }
  
  // 첨부파일 다운로드
  @GetMapping("/download.do")
  public ResponseEntity<Resource> download(@RequestParam("attachNo") int attachNo
                                         , @RequestHeader("User-Agent") String userAgent) {
    return approvalService.download(attachNo, userAgent);
  }
  
  @GetMapping("/change/record.do")
  public String changeRecord(HttpSession session
                           , HttpServletRequest request
                           , @RequestParam(value = "recordPerPage", required = false, defaultValue = "10") int recordPerPage) {
    session.setAttribute("recordPerPage", recordPerPage);
    System.out.println(recordPerPage + "recordPerPage");
    
    return "redirect:" + request.getHeader("referer");  
 //   return "redirect:/approval/approvalList.do?column=APPROVAL_NO&order=ASC&page=1";
  }  
  
  @GetMapping("/approvalList.do")
  // 컨트롤러에서 @@RequestParam으로 파라미터에 대한 null처리하기.
  // pagination에서 사용할 경로를 위해 HttpServletRequest, 속성 저장을 위해 Model을 사용한다.
  public String approvalList(HttpServletRequest request, Model model, HttpSession session) {
    
    approvalService.getApprovalList(request, model, session);
     
     return "approval/approvalList";
  }
  
  // 참조 문서 조회하기
  @GetMapping("/referenceList.do")
  public String referenceList(HttpServletRequest request, Model model, HttpSession session) {
    //session에 올라간 recordPerPage 값 날려주기
    if(request.getHeader("referer").contains("referenceList.do") == false) {
      request.getSession().removeAttribute("recordPerPage");
    }
    approvalService.getReferencelList(request, model, session);
    return "approval/referenceList";
    
  }
  @GetMapping(value="/autoComplete.do", produces="application/json")
  @ResponseBody
  public Map<String, Object> autoComplete(HttpServletRequest request, HttpSession session){ 
    
    return approvalService.getAutoComplete(request, session);
  
    
  } 
  @GetMapping(value="/referenceAutoComplete.do", produces="application/json")
  @ResponseBody
  public Map<String, Object> referenceAutoComplete(HttpServletRequest request, HttpSession session){ 
    
    return approvalService.getReferenceAutoComplete(request, session);
    
  } 
  
  // 결재하기
  @PostMapping("/submitApproval.do") 
  public String submitApproval(HttpServletRequest request, RedirectAttributes redirectAttributes) {
    
    redirectAttributes.addFlashAttribute("approvalResult", approvalService.approvalOfDoc(request));
    
    return "redirect:/approval/detailApproval.do?approvalNo=" + request.getParameter("approvalNo") + "&docName=" + request.getParameter("docName");
  }
  
  // 반려하기
  @PostMapping("/rejectApproval.do")
  public String rejectApproval(HttpServletRequest request, RedirectAttributes redirectAttributes) {
    
    redirectAttributes.addFlashAttribute("rejectResult", approvalService.rejectOfDoc(request));
    
    return "redirect:/approval/detailApproval.do?approvalNo=" + request.getParameter("approvalNo") + "&docName=" + request.getParameter("docName");
  }
  
  // 삭제하기
  @PostMapping("/removeApproval.do")
  public String removeApproval(HttpServletRequest request, RedirectAttributes redirectAttributes) {
    
    redirectAttributes.addFlashAttribute("removeResult", approvalService.removeApproval(request));
    
    return "redirect:/approval/approvalList.do";
  }
  
  
    
     
  }
  
  
  
  

