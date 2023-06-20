package com.gdu.workship.service;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpSession;

import org.springframework.stereotype.Service;
import org.springframework.ui.Model;

import com.gdu.workship.domain.MemberDTO;
import com.gdu.workship.domain.TodolistDTO;
import com.gdu.workship.mapper.TodolistMapper;

import lombok.RequiredArgsConstructor;

@RequiredArgsConstructor
@Service
public class TodolistServiceImpl implements TodolistService {

	private final TodolistMapper todolistMapper;
	
	@Override
	public void todolistPage(HttpServletRequest request, Model model) {
		HttpSession session = request.getSession();
		int memberNo = ((MemberDTO)session.getAttribute("loginMember")).getMemberNo();
		List<TodolistDTO> undo = todolistMapper.getUndoList(memberNo);
		List<TodolistDTO> doing = todolistMapper.getDoingList(memberNo);
		List<TodolistDTO> done = todolistMapper.getDoneList(memberNo);
		model.addAttribute("undo", undo);
		model.addAttribute("doing", doing);
		model.addAttribute("done", done);
	}
	
	@Override
	public Map<String, Object> updateState(HttpServletRequest request) {
		int todolistNo = Integer.parseInt(request.getParameter("todolistNo"));
		int todoState = Integer.parseInt(request.getParameter("todoState"));
		Map<String, Object> parameter = new HashMap<>();
		parameter.put("todolistNo", todolistNo);
		parameter.put("todoState", todoState);
		int updateResult = todolistMapper.updateState(parameter);
		Map<String, Object> map = new HashMap<>();
		map.put("updateResult", updateResult);
		return map;
	}
	
	@Override
	public Map<String, Object> removeTodo(int todolistNo) {
		Map<String, Object> map = new HashMap<>();
		int deleteResult = todolistMapper.removeTodoByTodolistNo(todolistNo);
		map.put("deleteResult", deleteResult);
		return map;
	}
	
	@Override
	public int addTodolist(HttpServletRequest request) {
		HttpSession session = request.getSession();
		Map<String, Object> parameter = new HashMap<>();
		parameter.put("memberNo", ((MemberDTO)session.getAttribute("loginMember")).getMemberNo());
		parameter.put("todoTitle", request.getParameter("todoTitle"));
		parameter.put("todoMemo", request.getParameter("todoMemo"));
		int addResult = todolistMapper.addTodolist(parameter);
		return addResult;
	}
	
	@Override
	public int deleteAllDone(HttpSession session) {
		int memberNo = ((MemberDTO)session.getAttribute("loginMember")).getMemberNo();
		int doneCount = todolistMapper.selectAllDoneCount(memberNo);
		int deleteResult = todolistMapper.deleteAllDone(memberNo);
		int result = 0;
		if(doneCount == deleteResult) result = 1;
		return result;
	}
	
	/*
	@Override
	public Map<String, Object> todoAjax(int memberNo) {
		Map<String, Object> map = new HashMap<>();
		List<TodolistDTO> undo = todolistMapper.getUndoList(memberNo);
		List<TodolistDTO> doing = todolistMapper.getDoingList(memberNo);
		List<TodolistDTO> done = todolistMapper.getDoneList(memberNo);
		if(undo.isEmpty()) map.put("undo", "noResult");
		else map.put("undo", undo);
		if(doing.isEmpty()) map.put("doing", "noResult");
		else map.put("doing", doing);
		if(done.isEmpty()) map.put("done",  "noResult");
		else map.put("done", done);
		return map;
	}
	*/
	
}
