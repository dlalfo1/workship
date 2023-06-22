package com.gdu.workship.service;

import java.util.Map;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpSession;

import org.springframework.ui.Model;

public interface TodolistService {

	public void todolistPage(HttpServletRequest request, Model model);
	// public Map<String, Object> updateState(HttpServletRequest request);
	public Map<String, Object> removeTodo(int todolistNo);
	public int addTodolist(HttpServletRequest request);
	public int deleteAllDone(HttpSession session);
	
	// public Map<String, Object> todoAjax(int memberNo);
	public void updateState(HttpServletRequest request);
}
