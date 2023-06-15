package com.gdu.workship.mapper;

import java.util.List;
import java.util.Map;

import com.gdu.workship.domain.BoardDTO;
import com.gdu.workship.domain.BoardFileDTO;
import com.gdu.workship.domain.MemberDTO;

public interface BoardMapper {
	
	public MemberDTO getMemberByEmail(String emailId);
	public int getBoardCount();
	public List<BoardDTO> getBoardList(Map<String, Object> map);
	public int getBoardSearchCount(Map<String, Object> map);
	
	public int increaseHit(int boardNo);
	
    public BoardDTO getboardByNo(int boardNo);
    public List<BoardFileDTO> getboardFileList(int boardNo);
  
    public BoardFileDTO getboardFileByNo(int boardFileNo);
  
    public int addBoard(BoardDTO boardDTO);
    public int addBoardFile(BoardFileDTO boardFileDTO);
  
    public int removeBoard(int boardNo);
  
    public int modifyBoard(BoardDTO boardDTO);
    public int removeBoardFile(int boardFileNo);
}
