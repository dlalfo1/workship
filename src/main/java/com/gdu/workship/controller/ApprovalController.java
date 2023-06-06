package com.gdu.workship.controller;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;

import lombok.RequiredArgsConstructor;

@RequiredArgsConstructor
@RequestMapping("/approval")
@Controller
public class ApprovalController {
  
  @GetMapping("/documentList.html")
  public String documentList(String form) {
 
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
