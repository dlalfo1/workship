package com.gdu.workship.controller;

import java.util.Map;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpSession;

import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;

import com.gdu.workship.service.VacationService;

import lombok.RequiredArgsConstructor;

@RequiredArgsConstructor
@RequestMapping("/vacation")
@Controller
public class VacationController {
	
	private final VacationService vacationService;
	
	@GetMapping("/vacationList.html")
	public String personalVacationList(HttpSession session, Model model) {
		vacationService.getPersonalVacationInfo(session, model);
		return "vacation/vacationList";
	}
	
	@GetMapping("/approvalSearch.do")
	public String approvalListPage(HttpServletRequest request, Model model) {
		vacationService.getApprovalList(request, model);
		return "vacation/vacationApprovalManage";
	}
	
	@ResponseBody
	@PostMapping("/updateApproval.do")
	public Map<String, Object> updateApproval(HttpServletRequest request){
		return vacationService.updateApproval(request);
	}
	
	@GetMapping("/vacationSearch.do")
	public String vacationListPage(HttpServletRequest request, Model model) {
		vacationService.getVacationList(request, model);
		return "vacation/vacationListManage";
	}
	
	@ResponseBody
	@PostMapping("/modifyVacation.do")
	public Map<String, Object> modifyVacation(HttpServletRequest request){
		return vacationService.modifyVacation(request);
	}

}
