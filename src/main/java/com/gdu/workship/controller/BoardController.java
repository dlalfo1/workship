package com.gdu.workship.controller;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpSession;

import org.springframework.core.io.Resource;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestHeader;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.multipart.MultipartHttpServletRequest;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;

import com.gdu.workship.domain.MemberDTO;
import com.gdu.workship.service.BoardService;

import lombok.RequiredArgsConstructor;

@RequiredArgsConstructor
@Controller 
public class BoardController {
	
	private final BoardService boardService;
	
	@GetMapping("/board/boardMain.html")
	public String board() {
		
		return "board/boardMain";
	}
	@GetMapping("/board/HRBoard.html")
	public String HRBoard() {
		return "board/HRBoard";
	}
	@GetMapping("/board/GADBoard.html")
	public String GADBoard() {
		return "board/GADBoard";
	}
	@GetMapping("/board/DEVBoard.html")
	public String DEVBoard() {
		return "board/DEVBoard";
	}
	
	@GetMapping("/board/boardList.do")
	public String boardMain(HttpServletRequest request, Model model) {
		boardService.loadBoardList(request, model);
		int deptNo = Integer.parseInt(request.getParameter("boardCategory"));
		String dept = null;
		if(deptNo == 1) dept = "HR";
		if(deptNo == 2) dept = "GAD";
		if(deptNo == 3) dept = "DEV";
		return "board/" + dept + "Board";
	}
	
	/*
	@GetMapping("/board/boardList.do")
	public String boardMain(HttpServletRequest request, Model model) {
	    boardService.loadBoardList(request, model);
	    String boardCategoryParam = request.getParameter("boardCategory");
	    int deptNo;
	    String dept = null;
	    if (boardCategoryParam != null) {
	        deptNo = Integer.parseInt(boardCategoryParam);
	        if (deptNo == 1) dept = "HR";
	        else if (deptNo == 2) dept = "GAD";
	        else if (deptNo == 3) dept = "DEV";
	    }
	    return "board/" + (dept != null ? dept : "Board");
	}
	*/
	 @GetMapping("/board/increaseHit.do")
	  public String increseHit(@RequestParam(value="boardNo", required=false, defaultValue="0") int boardNo) {
	    int increaseResult = boardService.increaseHit(boardNo);
	    if(increaseResult == 1) {
	      return "redirect:/board/boardDetail.html?boardNo=" + boardNo;
	    } else {
	      return "redirect:/board/boardList.do";
	    }
	  }
	  
	  @GetMapping("/board/boardDetail.html")
	  public String boardDetail(@RequestParam(value="boardNo", required=false, defaultValue="0") int boardNo, HttpServletRequest request, Model model) {
	    boardService.getBoardByNo(boardNo, model);
	    return "board/boardDetail";
	  }
	  
	  @GetMapping("/board/download.do")
	  public ResponseEntity<Resource> download(@RequestParam("boardFileNo") int boardFileNo, @RequestHeader("User-Agent") String userAgent) {
	    return boardService.download(boardFileNo, userAgent);
	  }
	  
	  @GetMapping("/board/downloadAll.do")
	  public ResponseEntity<Resource> downloadAll(@RequestParam("boardNo") int boardNo) {
	    return boardService.downloadAll(boardNo);
	  }
	  
		@GetMapping("/board/boardWrite.html")
		public String write(HttpServletRequest request, HttpSession session, Model model) {
		  MemberDTO member = boardService.goWrite(session, model);
		  model.addAttribute("boardCategory", request.getParameter("boardCategory"));
		  model.addAttribute("member", member);
			return "board/boardWrite";
		}
		
		@PostMapping("/board/addBoard.do")
		public String addBoard(MultipartHttpServletRequest request, RedirectAttributes redirectAttributes) {
			int boardCategory = Integer.parseInt(request.getParameter("boardCategory"));
			int addResult = boardService.addBoard(request);
			redirectAttributes.addFlashAttribute("addResult", addResult);
			return "redirect:/board/boardList.do?boardCategory=" + boardCategory;
		}
	
	  @PostMapping("/board/removeBoard.do")
	  public String removeBoard(@RequestParam("boardNo") int boardNo, HttpServletRequest request, RedirectAttributes redirectAttributes) { 
		int boardCategory = Integer.parseInt(request.getParameter("boardCategory"));
	    int removeResult = boardService.removeBoard(boardNo);
	    redirectAttributes.addFlashAttribute("removeResult", removeResult);
	    
	    return "redirect:/board/boardList.do?boardCategory=" + boardCategory;
	  }
	
		
	  @PostMapping("/board/boardEdit.html")
	  public String boardEdit(@RequestParam("boardNo") int boardNo, Model model) {
	    boardService.getBoardByNo(boardNo, model);
	    return "board/boardEdit";
	  }
	  
	  @PostMapping("/board/modifyBoard.do")
	  public String modifyBoard(MultipartHttpServletRequest request, RedirectAttributes redirectAttributes) {
	    int modifyResult = boardService.modifyBoard(request);
	    redirectAttributes.addFlashAttribute("modifyResult", modifyResult);
	    
	    return "redirect:/board/boardDetail.html?boardNo=" + request.getParameter("boardNo");
	  }
	  
	  @GetMapping("/board/removeBoardFile.do")
	  public String removeBoardFile(@RequestParam("boardNo") int boardNo, @RequestParam("boardFileNo") int boardFileNo) {
	   boardService.removeBoardFile(boardFileNo);
	    return "redirect:/board/boardDetail.html?boardNo=" + boardNo;
	  }
		
	}

