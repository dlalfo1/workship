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

import com.gdu.workship.service.ProjectService;

import lombok.RequiredArgsConstructor;

@RequestMapping("/project")
@RequiredArgsConstructor
@Controller
public class ProjectController {
	
	private final ProjectService projectService;
	
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
	
	@PostMapping("/projectAdd.do")
	public String projectAdd(MultipartHttpServletRequest multipartrequest, RedirectAttributes redirectAttributes) {
		redirectAttributes.addFlashAttribute("addResult", projectService.addProjectM(multipartrequest));
		return "redirect:/project/projectList.do";
	}
	
	@GetMapping("/project/projectWorkDetail.html")
	public String projectWorkDetail(@RequestParam("projectWorkNo") int projectWorkNo, Model model) {
		projectService.projectWDetail(projectWorkNo, model);
		return "project/projectWorkDetail";
	}

	@PostMapping("/project/projectUpdate.do")
	public String projectUpdate(MultipartHttpServletRequest request, RedirectAttributes redirectAttributes) {
		redirectAttributes.addFlashAttribute("modifyResult", projectService.updateProjectM(request));
		return "redirect:/project/projectList.do";
	}
	
	@PostMapping("/project/projectWorkUpdate.do")
	public String projectWorkUpdate(MultipartHttpServletRequest request, RedirectAttributes redirectAttributes) {
		redirectAttributes.addFlashAttribute("modifyResult", projectService.updateProjectW(request));
		return "redirect:/project/projectWorkList.do";
	}
	
	@PostMapping("/project/deleteProject.do")
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
