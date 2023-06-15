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
	
	@GetMapping("/board/boarList.do")
	public String boardMain(HttpServletRequest request, Model model) {
		boardService.loadBoardList(request, model);
		return "board/boardMain";
	}
	
	 @GetMapping("/board/increaseHit.do")
	  public String increseHit(@RequestParam(value="noticeNo", required=false, defaultValue="0") int boardNo) {
	    int increaseResult = BoardService.increaseHit(boardNo);
	    if(increaseResult == 1) {
	      return "redirect:/board/boardDetail.html?boardNo=" + boardNo;
	    } else {
	      return "redirect:/board/boardList2.do";
	    }
	  }
	  
	  @GetMapping("/board/boardDetail.html")
	  public String boardDetail(@RequestParam(value="boardNo", required=false, defaultValue="0") int boardNo, Model model) {
	    BoardService.getBoardByNo(boardNo, model);
	    return "board/boardDetail";
	  }
	  
	  @GetMapping("/board/download.do")
	  public ResponseEntity<Resource> download(@RequestParam("boardFileNo") int boardFileNo, @RequestHeader("User-Agent") String userAgent) {
	    return BoardService.download(boardFileNo, userAgent);
	  }
	  
	  @GetMapping("/board/downloadAll.do")
	  public ResponseEntity<Resource> downloadAll(@RequestParam("boardNo") int boardNo) {
	    return BoardService.downloadAll(boardNo);
	  }
	  
		@GetMapping("/board/board.html")
		public String write(HttpSession session, Model model) {
		  MemberDTO member = BoardService.goWrtie(session, model);
		  model.addAttribute("member", member);
			return "board/boardWrite";
		}
		
		@PostMapping("/board/addBoard.do")
		public String addBoard(MultipartHttpServletRequest request, RedirectAttributes redirectAttributes) {
		  int addResult = BoardService.addBoard(request);
	    redirectAttributes.addFlashAttribute("addResult", addResult);
		  return "redirect:/board/boardList.do";
		}
		
	  @PostMapping("/board/removeNotice.do")
	  public String removeNotice(@RequestParam("noticeNo") int noticeNo, RedirectAttributes redirectAttributes) { 
	    int removeResult = BoardService.removeBoard(boardNo);
	    redirectAttributes.addFlashAttribute("removeResult", removeResult);
	    return "redirect:/board/boardList.do";
	  }
		
	  @PostMapping("/board/editboard.html")
	  public String editNotice(@RequestParam("boardNo") int boardNo, Model model) {
	    BoardService.getBoardByNo(boardNo, model);
	    return "notice/noticeEdit";
	  }
	  
	  @PostMapping("/board/modifyBoard.do")
	  public String modifyNotice(MultipartHttpServletRequest request, RedirectAttributes redirectAttributes) {
	    int modifyResult = BoardService.modifyBoard(request);
	    redirectAttributes.addFlashAttribute("modifyResult", modifyResult);
	    return "redirect:/board/boardDetail.html?boardNo=" + request.getParameter("boardNo");
	  }
	  
	  @GetMapping("/notice/removeNoticeFile.do")
	  public String removeBoardFile(@RequestParam("boardNo") int boardNo, @RequestParam("boardFileNo") int boardFileNo) {
	    BoardService.removeBoardFile(boardFileNo);
	    return "redirect:/board/boardDetail.html?boardNo=" + boardNo;
	  }
		
	}

