package com.gdu.workship.mapper;

import java.util.List;
import java.util.Map;

import org.apache.ibatis.annotations.Mapper;

import com.gdu.workship.domain.BoardCommentDTO;

@Mapper
public interface BoardCommentMapper {
	public int getCommentCount(int boardNo);
	public List<BoardCommentDTO> getCommentList(Map<String, Object> map);
	public int addComment(BoardCommentDTO boardCommentDTO);
	public int addCommentGroupNo(BoardCommentDTO boardCommentDTO);
	public int removeComment(int commentNo);
	public int increaseGroupOrder(BoardCommentDTO boardCommentDTO);
	public int addReply(BoardCommentDTO replyDTO);
}
