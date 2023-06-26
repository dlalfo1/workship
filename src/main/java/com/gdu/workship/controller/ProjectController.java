package com.gdu.workship.controller;

import javax.servlet.http.HttpServletRequest;

import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;

import com.gdu.workship.domain.MemberDTO;
import com.gdu.workship.domain.ProjectDTO;
import com.gdu.workship.domain.ProjectWorkDTO;
import com.gdu.workship.mapper.NoticeBoardMapper;
import com.gdu.workship.mapper.ProjectMapper;
import com.gdu.workship.service.ProjectService;

import lombok.RequiredArgsConstructor;

@RequestMapping("/project")
@RequiredArgsConstructor
@Controller
public class ProjectController {

	private final ProjectService projectService;
	private final NoticeBoardMapper noticeBoardMapper;
	private final ProjectMapper projectMapper;
	
	@GetMapping("/projectList.do")
	public String projectList(HttpServletRequest request, Model model) {
		projectService.getProjectList(request, model);
		return "project/projectMain";
	}
	
	@GetMapping(value="/projectWorkList.html")
	public String projectWorkList(HttpServletRequest request, Model model) {
		String memberName = request.getParameter("memberName");
		String deptName = request.getParameter("deptName");
		projectService.getProjectWorkList(request, model);	
		
		model.addAttribute("memberName", memberName);
		model.addAttribute("deptName", deptName);
		
		return "project/projectWorkList";
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
		int projectNo = Integer.parseInt(request.getParameter("projectNo"));
		ProjectDTO projectDTO = new ProjectDTO();
		projectDTO = projectMapper.getProject(projectNo);
		
		MemberDTO member = new MemberDTO();
		member = (MemberDTO)request.getSession().getAttribute("loginMember");
		String emailId = member.getEmailId();
		model.addAttribute("member", noticeBoardMapper.getMemberByEmail(emailId)); 
		model.addAttribute("projectDTO" , projectDTO);
		return "project/projectAdd"; 
	}
	
	@PostMapping("/projectAdd.do")
	public String projectAdd(HttpServletRequest request, RedirectAttributes redirectAttributes) {
		redirectAttributes.addFlashAttribute("addResult", projectService.addProjectM(request));
		return "redirect:/project/projectList.do";
	}
	
	@GetMapping("/projectWorkDetail.html")
	public String projectWorkDetail(HttpServletRequest request, Model model) {
		MemberDTO member = new MemberDTO();
		member = (MemberDTO)request.getSession().getAttribute("loginMember");
		String emailId = member.getEmailId();
		model.addAttribute("member", noticeBoardMapper.getMemberByEmail(emailId)); 
		int projectWorkNo = Integer.parseInt(request.getParameter("projectWorkNo"));
		int projectNo = Integer.parseInt(request.getParameter("projectNo"));
		ProjectWorkDTO projectWorkDTO =  projectMapper.getProjectWByNo(projectWorkNo);
		ProjectDTO projectDTO = projectMapper.getProject(projectNo);
		model.addAttribute("projectWorkDTO", projectWorkDTO);
		model.addAttribute("projectDTO", projectDTO);
		projectService.projectWDetail(projectWorkNo, model);
		return "project/projectWorkDetail";
	}

	@PostMapping("/projectUpdate.do")
	public String projectUpdate(HttpServletRequest request, RedirectAttributes redirectAttributes) {
		redirectAttributes.addFlashAttribute("modifyResult", projectService.updateProjectM(request));
		return "redirect:/project/projectList.do";
	}
	
	@PostMapping("/projectWorkUpdate.do")
	public String projectWorkUpdate(HttpServletRequest request, RedirectAttributes redirectAttributes) {
		int projectNo = Integer.parseInt(request.getParameter("projectNo")); 
		redirectAttributes.addFlashAttribute("modifyResult", projectService.updateProjectW(request));
		return "redirect:/project/projectWorkList.html?projectNo=" + projectNo;
	}
	
	@PostMapping("/deleteProject.do")
	public String deleteProject(@RequestParam("projectNo") int projectNo, RedirectAttributes redirectAttributes) {
		redirectAttributes.addFlashAttribute("deleteResult", projectService.deleteProjectM(projectNo));
		return "redirect:/project/projectList.do";
	}
	
	@PostMapping("/deleteProjectWork.do")
	public String deleteProjectWork(HttpServletRequest request, RedirectAttributes redirectAttributes, Model model) {
		int projectWorkNo = Integer.parseInt(request.getParameter("projectWorkNo"));
		int projectNo = Integer.parseInt(request.getParameter("projectNo"));
		redirectAttributes.addFlashAttribute("deleteResult", projectService.deleteProjectW(projectWorkNo));
		
		projectService.getProjectWorkList(request, model);	
		
		return "redirect:/project/projectWorkList.html?projectNo=" + projectNo;
	}
	
	@GetMapping("/projectWorkAdd.html")
	public String projectWorkAdd(HttpServletRequest request, Model model) {
		MemberDTO member = new MemberDTO();
		member = (MemberDTO)request.getSession().getAttribute("loginMember");
		String emailId = member.getEmailId();
		model.addAttribute("member", noticeBoardMapper.getMemberByEmail(emailId)); 
		int projectNo = Integer.parseInt(request.getParameter("projectNo"));
		ProjectDTO projectDTO = new ProjectDTO();
		projectDTO = projectMapper.getProject(projectNo);
		model.addAttribute("projectDTO" , projectDTO);
		model.addAttribute("projectNo", projectNo);
		return "project/projectWorkAdd";
	}
	
	@GetMapping("/projectWorkUpdate.html")
	public String projectWorkUpdate(HttpServletRequest request, Model model) {
		MemberDTO member = new MemberDTO();
		member = (MemberDTO)request.getSession().getAttribute("loginMember");
		String emailId = member.getEmailId();
		model.addAttribute("member", noticeBoardMapper.getMemberByEmail(emailId)); 
		int projectWorkNo = Integer.parseInt(request.getParameter("projectWorkNo"));
		ProjectWorkDTO projectWorkDTO =  projectMapper.getProjectWByNo(projectWorkNo);
		model.addAttribute("projectWorkDTO", projectWorkDTO);
		System.out.println("프로젝트:::" + projectWorkDTO);
		
		return "project/projectWorkUpdate";
	}
	
	@PostMapping("/projectWorkAdd.do")
	public String projectWorkAddDo(HttpServletRequest request, RedirectAttributes redirectAttributes) {
		int projectNo = Integer.parseInt(request.getParameter("projectNo"));
		redirectAttributes.addFlashAttribute("addResult", projectService.addProjectW(request));
		return "redirect:/project/projectWorkList.html?projectNo=" + projectNo;
	}
	
	
}
