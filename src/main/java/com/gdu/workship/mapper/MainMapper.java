package com.gdu.workship.mapper;

import java.util.List;
import java.util.Map;

import org.apache.ibatis.annotations.Mapper;

import com.gdu.workship.domain.ApprovalDTO;
import com.gdu.workship.domain.BoardDTO;
import com.gdu.workship.domain.NoticeDTO;
import com.gdu.workship.domain.TodolistDTO;

@Mapper
public interface MainMapper {
	
	public List<NoticeDTO> getRecentNoticeList();
	public List<BoardDTO> getRecentBoardList(int boardCategory);
	public int getMailNoReadCount(String emailId);
	public List<TodolistDTO> getRecentTodolist(int memberNo);
	public List<TodolistDTO> getTodoListByStatus(Map<String, Object> map);
	public List<ApprovalDTO> getRecentApprovalList(int memberNo);
	
}
