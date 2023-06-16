package com.gdu.workship.controller;

import javax.servlet.http.HttpServletRequest;

import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;

import com.gdu.workship.service.DepartmentService;

import lombok.RequiredArgsConstructor;

@RequiredArgsConstructor
@Controller
public class DepartmentController {
  
  private final DepartmentService departmentService;
  
  @GetMapping("/department/deptList.html")
  public String deptList(HttpServletRequest request, Model model) {
    departmentService.loadDepartmentList(request, model);
    return "department/dept";
  }
  
  @PostMapping("/department/addDept.do")
  public String addDept(HttpServletRequest request, RedirectAttributes redirectAttributes) {
    int addResult = departmentService.addDept(request);
    redirectAttributes.addFlashAttribute("addResult", addResult);
    return "redirect:/department/deptList.html";
  }
  
  @GetMapping("/department/removeDept.do")
  public String removeDept(HttpServletRequest request, RedirectAttributes redirectAttributes) {
    int removeResult = departmentService.removeDept(request);
    redirectAttributes.addFlashAttribute("removeResult", removeResult);
    return "redirect:/department/deptList.html";
  }

}
