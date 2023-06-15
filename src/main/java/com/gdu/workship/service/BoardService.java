package com.gdu.workship.service;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpSession;

import org.springframework.core.io.Resource;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;
import org.springframework.ui.Model;
import org.springframework.web.multipart.MultipartHttpServletRequest;

import com.gdu.workship.domain.MemberDTO;

@Service
public interface BoardService {
  public void loadBoardList(HttpServletRequest request, Model model);
  public int increaseHit(int boardNo);
  public void getBoardByNo(int boardNo, Model model);
  public ResponseEntity<Resource> download(int boardFileNo, String userAgent);
  public ResponseEntity<Resource> downloadAll(int boardNo);
  public MemberDTO goWrtie(HttpSession session, Model model);
  public int addBoard(MultipartHttpServletRequest request);
  public int removeBoard(int boardNo);
  public int modifyBoard(MultipartHttpServletRequest multipartRequest);
  public int removeBoardFile(int boardFileNo);
}
