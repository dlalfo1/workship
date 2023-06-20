package com.gdu.workship.mapper;

import java.util.List;
import java.util.Map;

import org.apache.ibatis.annotations.Mapper;

import com.gdu.workship.domain.TodolistDTO;

@Mapper
public interface TodolistMapper {
	
	public List<TodolistDTO> getUndoList(int memberNo);
	public List<TodolistDTO> getDoingList(int memberNo);
	public List<TodolistDTO> getDoneList(int memberNo);
	public int updateState(Map<String, Object> map);
	public int removeTodoByTodolistNo(int todolistNo);
	public int addTodolist(Map<String, Object> map);
	public int selectAllDoneCount(int memberNo);
	public int deleteAllDone(int memberNo);
	
}
