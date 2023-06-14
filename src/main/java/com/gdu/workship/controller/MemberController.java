package com.gdu.workship.controller;

import java.util.Map;

import javax.servlet.http.HttpServletRequest;

import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.multipart.MultipartHttpServletRequest;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;

import com.gdu.workship.service.MemberService;

import lombok.RequiredArgsConstructor;

@RequiredArgsConstructor
@Controller
public class MemberController {

  private final MemberService memberService;

  
  @GetMapping("/member/memberList.do") 
  public String memberList(HttpServletRequest request, Model model) {
    memberService.loadMemberList(request, model);
    return "member/member";
  }

  @GetMapping(value="/member/memberList2.do", produces="application/json")
  @ResponseBody
  public Map<String, Object> memberList2(HttpServletRequest request) {
   return memberService.loadMemberList2(request);
  }
  
  @GetMapping(value="/member/retiredMemberList.do", produces="application/json")
  @ResponseBody
  public Map<String, Object> retiredMemberList(HttpServletRequest request) {
    return memberService.loadRetiredMemberList(request);
  }
  
  @PostMapping("/member/addMember.do")
  public String addMember(MultipartHttpServletRequest request, RedirectAttributes redirectAttributes) {
    redirectAttributes.addFlashAttribute("addReuslt", memberService.addMember(request));
    return "redirect:/member/memberList.do";
  }
  
  @GetMapping("/member/memberDetail.html")
  public String editMember(@RequestParam("memberNo") int memberNo, Model model) {
    memberService.memberDetail(memberNo, model);
    return "/member/memberDetail";
  }
  
  @PostMapping("/member/modifyMember.do")
  public String modifyMember(MultipartHttpServletRequest request, RedirectAttributes redirectAttributes) {
    System.out.println(memberService.modifyMember(request));
    redirectAttributes.addFlashAttribute("modifyResult", memberService.modifyMember(request));
    return "redirect:/member/memberList.do";
  }
  @GetMapping("/member/display.do")
  public ResponseEntity<byte[]> display(@RequestParam("memberNo") int memberNo) {
    return memberService.display(memberNo);
  }
  
  @PostMapping("/member/removeMember.do")
  public String removeMember(@RequestParam("memberNo") int memberNo, RedirectAttributes redirectAttributes) {
    redirectAttributes.addFlashAttribute("removeResult", memberService.removeMember(memberNo));
    return "redirect:/member/memberList.do";
  }

}
