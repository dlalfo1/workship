package com.gdu.workship.controller;

import java.util.Map;

import javax.servlet.http.HttpServletRequest;

import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;

import com.gdu.workship.service.DepartmentService;

import lombok.RequiredArgsConstructor;

@RequiredArgsConstructor
@Controller
public class DepartmentController {
  
  private final DepartmentService departmentService;
  
  @GetMapping("/department/dept.html")
  public String deptList(HttpServletRequest request, Model model) {
    departmentService.loadDepartmentList(request, model);
    return "department/dept";
  }
  
  @GetMapping(value="/department/loadDeptNameBack.do", produces="application/json")
  @ResponseBody
  public Map<String, Object> loadDeptNameBack(HttpServletRequest request) {
    return departmentService.loadDeptNameBack(request);
  }
  
  @PostMapping("/department/addDept.do")
  public String addDept(HttpServletRequest request, RedirectAttributes redirectAttributes) {
    int addResult = departmentService.addDept(request);
    redirectAttributes.addFlashAttribute("addResult", addResult);
    return "redirect:/department/deptList.html";
  }
  
  @PostMapping(value="/department/modifyDept.do", produces="application/json")
  @ResponseBody
  public Map<String, Object> modifyDept(HttpServletRequest request) {
    return departmentService.modifyDept(request);
  }
  
  @GetMapping("/department/removeDept.do")
  public String removeDept(HttpServletRequest request, RedirectAttributes redirectAttributes) {
    int removeResult = departmentService.removeDept(request);
    redirectAttributes.addFlashAttribute("removeResult", removeResult);
    return "redirect:/department/deptList.html";
  }

}
