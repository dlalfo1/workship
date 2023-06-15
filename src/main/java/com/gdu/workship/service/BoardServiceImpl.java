package com.gdu.workship.service;

import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Optional;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpSession;

import org.springframework.core.io.Resource;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;
import org.springframework.ui.Model;
import org.springframework.web.multipart.MultipartHttpServletRequest;

import com.gdu.workship.domain.BoardDTO;
import com.gdu.workship.domain.MemberDTO;
import com.gdu.workship.domain.NoticeDTO;
import com.gdu.workship.mapper.BoardMapper;
import com.gdu.workship.mapper.NoticeBoardMapper;
import com.gdu.workship.util.MyFileUtil;
import com.gdu.workship.util.PageUtil;

import lombok.RequiredArgsConstructor;

@RequiredArgsConstructor
@Service
public class BoardServiceImpl implements BoardService {

	private final BoardMapper boardMapper;
	private final PageUtil pageUtil;
	private final MyFileUtil myFileUtil;
	
	
	@Override
	public void loadBoardList(HttpServletRequest request, Model model) {

	    Optional<String> opt1 = Optional.ofNullable(request.getParameter("page"));
	    int page = Integer.parseInt(opt1.orElse("1"));
	    
	    // 파라미터 column이 전달되지 않는 경우 column=""로 처리한다. (column이 없으면 null값, 처음 화면이동 요청..)
	    Optional<String> opt2 = Optional.ofNullable(request.getParameter("column"));
	    String column = opt2.orElse("");
	    
	    // 파라미터 query가 전달되지 않는 경우 query=""로 처리한다. (query가 없으면 null값, 처음 화면이동 요청..)
	    Optional<String> opt3 = Optional.ofNullable(request.getParameter("query"));
	    String query = opt3.orElse("");
	    
	    int totalRecord = noticeBoardMapper.getNoticeCount();
	    
	    int recordPerPage = 10;
	    
	    Map<String, Object> map = new HashMap<String, Object>();
	    map.put("column", column);
	    map.put("query", query);
	    pageUtil.setPageUtil(page, (column.isEmpty() || query.isEmpty()) ? totalRecord : noticeBoardMapper.getNoticeSearchCount(map), recordPerPage);
	    
	    map.put("begin", pageUtil.getBegin());      
	    map.put("recordPerPage", recordPerPage);
	    System.out.println(map);
	    
	    List<BoardDTO> boardList = boardMapper.getBoardList(map);
	    System.out.println(boardList);
	    model.addAttribute("boardList", boardList);
	    model.addAttribute("beginNo", totalRecord - (page - 1) * recordPerPage);
	    if(column.isEmpty() || query.isEmpty()) {
	      model.addAttribute("pagination", pageUtil.getPagination(request.getContextPath() + "/board/boardList.do"));
	    } else {
	      model.addAttribute("pagination", pageUtil.getPagination(request.getContextPath() + "/board/boardList.do?column=" + column + "&query=" + query));
	    }
	}

	@Override
	public int increaseHit(int boardNo) {
		return 0;
	}

	@Override
	public void getBoardByNo(int boardNo, Model model) {

	}

	@Override
	public ResponseEntity<Resource> download(int boardFileNo, String userAgent) {
		return null;
	}

	@Override
	public ResponseEntity<Resource> downloadAll(int boardNo) {
		return null;
	}

	@Override
	public MemberDTO goWrtie(HttpSession session, Model model) {
		return null;
	}

	@Override
	public int addBoard(MultipartHttpServletRequest request) {
		return 0;
	}

	@Override
	public int removeBoard(int boardNo) {
		return 0;
	}

	@Override
	public int modifyBoard(MultipartHttpServletRequest multipartRequest) {
		return 0;
	}

	@Override
	public int removeBoardFile(int boardFileNo) {
		return 0;
	}

}
