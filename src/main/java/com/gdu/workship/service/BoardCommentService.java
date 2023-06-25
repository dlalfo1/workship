package com.gdu.workship.service;

import java.util.Map;

import javax.servlet.http.HttpServletRequest;

import org.springframework.core.io.Resource;
import org.springframework.http.ResponseEntity;

import com.gdu.workship.domain.BoardCommentDTO;

public interface BoardCommentService {
	
	public Map<String, Object> addComment(HttpServletRequest request);
	public Map<String, Object> addReply(HttpServletRequest request);
	public Map<String, Object> getCommentCount(int boardNo);
	public Map<String, Object> getCommentList(HttpServletRequest request);
	public int removeComment(BoardCommentDTO boardCommentDTO);
}
	