package com.gdu.workship.controller;

import java.util.Map;

import javax.servlet.http.HttpServletRequest;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;

import com.gdu.workship.domain.BoardCommentDTO;
import com.gdu.workship.service.BoardCommentService;

import lombok.RequiredArgsConstructor;

@RequiredArgsConstructor
@RequestMapping("/boardComment")
@Controller
public class BoardCommentController {

	private final BoardCommentService boardCommentService;
	  
	
	  @PostMapping(value="/addComment.do", produces="application/json")
	  @ResponseBody 
	  public Map<String, Object> addComment(HttpServletRequest request) {
	    return boardCommentService.addComment(request);
	  }
	  
	  @GetMapping(value="/list.do", produces="application/json")
	  @ResponseBody
	  public Map<String, Object> list(HttpServletRequest request) {
	    return boardCommentService.getCommentList(request);
	  }
	  
	  @PostMapping(value="/addReply.do", produces="application/json")
	  @ResponseBody
	  public Map<String, Object> addReply(HttpServletRequest request) {
	    return boardCommentService.addReply(request);
	  }
	  
	  @PostMapping(value="/removeComment.do", produces="application/json")
	  @ResponseBody
	  public void removeComment(BoardCommentDTO boardCommentDTO) {
		  boardCommentService.removeComment(boardCommentDTO);
	  }
		  
}
	  

