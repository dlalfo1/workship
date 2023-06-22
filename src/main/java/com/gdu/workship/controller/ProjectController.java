package com.gdu.workship.controller;

import java.util.Map;

import javax.servlet.http.HttpServletRequest;

import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.multipart.MultipartHttpServletRequest;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;

import com.gdu.workship.domain.DepartmentDTO;
import com.gdu.workship.domain.MemberDTO;
import com.gdu.workship.domain.ProjectDTO;
import com.gdu.workship.mapper.NoticeBoardMapper;
import com.gdu.workship.service.ProjectService;

import lombok.RequiredArgsConstructor;

@RequestMapping("/project")
@RequiredArgsConstructor
@Controller
public class ProjectController {
	
	private final ProjectService projectService;
	private final NoticeBoardMapper noticeBoardMapper;
	
	@GetMapping("/projectList.do")
	public String projectList(HttpServletRequest request, Model model) {
		projectService.getProjectList(request, model);
		return "project/projectMain";
	}
	
	@GetMapping(value="/project/projectWorkList.do", produces="application/json")
	@ResponseBody
	public Map<String, Object> projectWorkList(HttpServletRequest request) {
		return projectService.getProjectWorkList(request);
	}
	
	@GetMapping("/projectUpdate.html")
	public String projectUpdateHtml(HttpServletRequest request, Model model) {
		int projectNo = Integer.parseInt(request.getParameter("projectNo"));

		ProjectDTO projectDTO = new ProjectDTO();
		projectDTO.setProjectNo(projectNo);
		
		MemberDTO member = new MemberDTO();
		member = (MemberDTO)request.getSession().getAttribute("loginMember");
		String emailId = member.getEmailId();
		
		model.addAttribute("member", noticeBoardMapper.getMemberByEmail(emailId));
		model.addAttribute("projectDTO", projectDTO);
		
		return "project/projectUpdate";
	}
	
	@GetMapping("/projectAdd.html")
	public String projectAddHtml(HttpServletRequest request, Model model) {
		MemberDTO member = new MemberDTO();
		member = (MemberDTO)request.getSession().getAttribute("loginMember");
		String emailId = member.getEmailId();
		model.addAttribute("member", noticeBoardMapper.getMemberByEmail(emailId)); 
		return "project/projectAdd"; 
	}
	
	@PostMapping("/projectAdd.do")
	public String projectAdd(HttpServletRequest request, RedirectAttributes redirectAttributes) {
		redirectAttributes.addFlashAttribute("addResult", projectService.addProjectM(request));
		return "redirect:/project/projectList.do";
	}
	
	@GetMapping("/project/projectWorkDetail.html")
	public String projectWorkDetail(@RequestParam("projectWorkNo") int projectWorkNo, Model model) {
		projectService.projectWDetail(projectWorkNo, model);
		return "project/projectWorkDetail";
	}

	@PostMapping("/projectUpdate.do")
	public String projectUpdate(HttpServletRequest request, RedirectAttributes redirectAttributes) {
		redirectAttributes.addFlashAttribute("modifyResult", projectService.updateProjectM(request));
		return "redirect:/project/projectList.do";
	}
	
	@PostMapping("/project/projectWorkUpdate.do")
	public String projectWorkUpdate(MultipartHttpServletRequest request, RedirectAttributes redirectAttributes) {
		redirectAttributes.addFlashAttribute("modifyResult", projectService.updateProjectW(request));
		return "redirect:/project/projectWorkList.do";
	}
	
	@PostMapping("/deleteProject.do")
	public String deleteProject(@RequestParam("projectNo") int projectNo, RedirectAttributes redirectAttributes) {
		redirectAttributes.addFlashAttribute("deleteResult", projectService.deleteProjectM(projectNo));
		return "redirect:/project/projectList.do";
	}
	
	@PostMapping("/project/deleteProjectWork.do")
	public String deleteProjectWork(@RequestParam("projectWorkNo") int projectWorkNo, RedirectAttributes redirectAttributes) {
		redirectAttributes.addFlashAttribute("deleteResult", projectService.deleteProjectM(projectWorkNo));
		return "redirect:/project/projectWorkList.do";
	}
	
	
}
