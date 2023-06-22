package com.gdu.workship.service;

import java.util.HashMap;
import java.util.Map;

import javax.servlet.http.HttpServletRequest;

import org.springframework.stereotype.Service;

import com.gdu.workship.domain.BoardCommentDTO;
import com.gdu.workship.domain.MemberDTO;
import com.gdu.workship.mapper.BoardCommentMapper;
import com.gdu.workship.util.PageUtil;

import lombok.RequiredArgsConstructor;

@RequiredArgsConstructor
@Service
public class BoardCommentImpl implements BoardCommentService {
		
		private final BoardCommentMapper boardCommentMapper;
		private final PageUtil pageUtil;
	
		@Override
		public Map<String, Object> addComment(HttpServletRequest request) {
		    String content = request.getParameter("commentContent");
		    int boardNo = Integer.parseInt(request.getParameter("boardNo"));
		    int memberNo = Integer.parseInt(request.getParameter("memberNo"));
		    MemberDTO memberDTO = new MemberDTO();
		    memberDTO.setMemberNo(memberNo);
		    
		    BoardCommentDTO boardCommentDTO = new BoardCommentDTO();
		    boardCommentDTO.setCommentContent(content);
		    boardCommentDTO.setBoardNo(boardNo);
		    boardCommentDTO.setMemberDTO(memberDTO);
		    System.out.println(boardCommentDTO);
		    Map<String, Object> map = new HashMap<String, Object>();
		    map.put("isAdd", boardCommentMapper.addComment(boardCommentDTO) == 1);
		    
		    return map;
		}
		@Override
		public Map<String, Object> addReply(HttpServletRequest request) {
		    
		    String commentContent = request.getParameter("commentContent");
		    int boardNo = Integer.parseInt(request.getParameter("boardNo"));
		    int commentGroupNo = Integer.parseInt(request.getParameter("commentGroupNo"));
		    int memberNo = Integer.parseInt(request.getParameter("memberNo"));
		    MemberDTO memberDTO = new MemberDTO();
		    memberDTO.setMemberNo(memberNo);
		    
		    BoardCommentDTO boardCommentDTO = new BoardCommentDTO();
		    boardCommentDTO.setCommentContent(commentContent);
		    boardCommentDTO.setBoardNo(boardNo);
		    boardCommentDTO.setCommentGroupNo(commentGroupNo);
		    boardCommentDTO.setMemberDTO(memberDTO);
		    
		    Map<String, Object> map = new HashMap<String, Object>();
		    map.put("isAdd", boardCommentMapper.addReply(boardCommentDTO) == 1);
		    
		    return map;
		}
		@Override
		public Map<String, Object> getCommentCount(int boardNo) {
		return null;
		}
		
		@Override
		public Map<String, Object> getCommentList(HttpServletRequest request) {
		    int boardNo = Integer.parseInt(request.getParameter("boardNo"));
		    int page = Integer.parseInt(request.getParameter("page"));
		    int commentCount = boardCommentMapper.getCommentCount(boardNo);
		    int recordPerPage = 999;
		    
		    pageUtil.setPageUtil(page, commentCount, recordPerPage);
		    
		    Map<String, Object> map = new HashMap<String, Object>();
		    map.put("boardNo", boardNo);
		    map.put("begin", pageUtil.getBegin());
		    map.put("recordPerPage", recordPerPage);
		    
		    Map<String, Object> result = new HashMap<String, Object>();
		    result.put("commentList", boardCommentMapper.getCommentList(map));
		    result.put("pageUtil", pageUtil);
		    
		    return result;
		    
		  }

}
