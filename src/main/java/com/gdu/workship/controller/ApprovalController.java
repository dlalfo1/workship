package com.gdu.workship.controller;

import java.text.SimpleDateFormat;
import java.time.LocalDate;
import java.util.Date;

import javax.servlet.http.HttpSession;

import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;

import com.gdu.workship.domain.DepartmentDTO;
import com.gdu.workship.domain.MemberDTO;

import lombok.RequiredArgsConstructor;

@RequiredArgsConstructor
@RequestMapping("/approval")
@Controller
public class ApprovalController {
  
  @GetMapping("/documentList.html")
  public String documentList(String form, HttpSession session, Model model) {
   // 세션에 memberDTO저장하기 전, 내가 저장해보기
    String memberName = "이미래";
    String deptName = "개발팀";
    Date date = new Date(System.currentTimeMillis()); // 오늘날짜 구하기. (입사날짜,작성일자)
    SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd");
    String today = sdf.format(date);
    
    MemberDTO memberDTO = new MemberDTO();
    DepartmentDTO departmentDTO = new DepartmentDTO();
    
    departmentDTO.setDeptName(deptName);
    memberDTO.setMemberName(memberName);
    memberDTO.setDepartmentDTO(departmentDTO);
    memberDTO.setJoinedAt(date); 
    
    model.addAttribute("member", memberDTO);
    model.addAttribute("department", departmentDTO);
    model.addAttribute("createdAt", today);
    
    // 만약 session에 위 정보가 저장되어있다면 바로 html에서 꺼내쓰면 됨.
    
    
    String result = "";
    switch(form) {
      case "휴가신청서":
        result = "approval/vacationDoc";
        break;
      case "지출결의서":
        result = "approval/expenseDoc";
        break;
      case "업무협조전":
        result = "approval/businessDoc";
        break;
      case "사직서":
        result = "approval/resignationDoc";
        break;
    }
    
    return result;
 
  }

}
