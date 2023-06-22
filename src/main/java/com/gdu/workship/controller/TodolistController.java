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

import com.gdu.workship.service.TodolistService;

import lombok.RequiredArgsConstructor;

@RequiredArgsConstructor
@RequestMapping("/todolist")
@Controller
public class TodolistController {

	private final TodolistService todolistService;

	@GetMapping("/todomain.do")
	public String todoMain(HttpServletRequest request, Model model) {
		todolistService.todolistPage(request, model);
		return "/todolist/todomain";
	}
	
	/*
	@GetMapping("/todoAjax.html")
	public String todoAjax() {
		return "/todolist/todomainAjax";
	}
	
	@ResponseBody
	@GetMapping("/getTodolist.do")
	public Map<String, Object> todoAjax(@RequestParam("memberNo") int memberNo){
		return todolistService.todoAjax(memberNo);
	}
	
	
	@ResponseBody
	@GetMapping("/updateStateAjax.do")
	public Map<String, Object> updateStateAjax(HttpServletRequest request){
		return todolistService.updateState(request);
	}
	*/
	
	@GetMapping("/updateState.do")
	public String updateState(HttpServletRequest request) {
		todolistService.updateState(request);
		return "redirect:/todolist/todomain.do";
	}
	
	@ResponseBody
	@PostMapping("/removeTodo.do")
	public Map<String, Object> removeTodo(@RequestParam("todolistNo") int todolistNo){
		return todolistService.removeTodo(todolistNo);
	}
	
	
	@PostMapping("/addTodolist.do")
	public String addTodolist(HttpServletRequest request, RedirectAttributes redirectAttributes) {
		int addResult = todolistService.addTodolist(request);
		redirectAttributes.addFlashAttribute("addResult", addResult);
		return "redirect:/todolist/todomain.do";
	}
	
	@GetMapping("/deleteAllDone.do")
	public String deleteAllDone(HttpSession session, RedirectAttributes redirectAttributes) {
		int result = todolistService.deleteAllDone(session);
		redirectAttributes.addFlashAttribute("result", result);
		return "redirect:/todolist/todomain.do";
	}
	
}
