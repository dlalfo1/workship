package com.gdu.workship.service;

import java.io.BufferedInputStream;
import java.io.File;
import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.net.URLEncoder;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Optional;
import java.util.zip.ZipEntry;
import java.util.zip.ZipOutputStream;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpSession;

import org.springframework.core.io.FileSystemResource;
import org.springframework.core.io.Resource;
import org.springframework.http.ContentDisposition;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.ui.Model;
import org.springframework.web.multipart.MultipartFile;
import org.springframework.web.multipart.MultipartHttpServletRequest;

import com.gdu.workship.domain.BoardDTO;
import com.gdu.workship.domain.BoardFileDTO;
import com.gdu.workship.domain.MemberDTO;
import com.gdu.workship.domain.NoticeDTO;
import com.gdu.workship.domain.NoticeFileDTO;
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
	    
	    int totalRecord = boardMapper.getBoardCount();
	    
	    int recordPerPage = 10;
	    
	    Map<String, Object> map = new HashMap<String, Object>();
	    map.put("column", column);
	    map.put("query", query);
	    pageUtil.setPageUtil(page, (column.isEmpty() || query.isEmpty()) ? totalRecord : boardMapper.getBoardSearchCount(map), recordPerPage);
	    
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
		return boardMapper.increaseHit(boardNo);
	}

	@Override
	public void getBoardByNo(int boardNo, Model model) {
		model.addAttribute("b", boardMapper.getBoardByNo(boardNo));
	    System.out.println(boardMapper.getBoardByNo(boardNo));
	    model.addAttribute("boardFileList", boardMapper.getBoardFileList(boardNo));
	}

	@Override
	public ResponseEntity<Resource> download(int boardFileNo, String userAgent) {
		// 다운로드 할 첨부 파일의 정보(경로, 원래 이름, 저장된 이름) 가져오기
	    BoardFileDTO boardFileDTO = boardMapper.getBoardFileByNo(boardFileNo);
	    
	    // 다운로드 할 첨부 파일의 File 객체 -> Resource 객체
	    File file = new File(boardFileDTO.getBoardFilePath(), boardFileDTO.getBoardFileSystemName());
	    Resource resource = new FileSystemResource(file);
	    
	    // 다운로드 할 첨부 파일의 존재 여부 확인(다운로드 실패를 반환)
	    if(resource.exists() == false) {
	      return new ResponseEntity<Resource>(HttpStatus.NOT_FOUND);
	    }
	    
	    // 다운로드 되는 파일명(첨부 파일의 원래 이름, UserAgent(브라우저)에 따른 인코딩 세팅)
	    String originName = boardFileDTO.getBoardFileOriginName();
	    try {
	      
	      // IE (UserAgent에 Trident가 포함되어 있다.)
	      if(userAgent.contains("Trident")) {
	        originName = URLEncoder.encode(originName, "UTF-8").replace("+", " ");
	      }
	      // Edge (UserAgent에 Edg가 포함되어 있다.)
	      else if(userAgent.contains("Edg")) {
	        originName = URLEncoder.encode(originName, "UTF-8");
	      }
	      // Other
	      else {
	        originName = new String(originName.getBytes("UTF-8"), "ISO-8859-1");
	      }
	      
	    } catch(Exception e) {
	      e.printStackTrace();
	    }
	    
	    // 다운로드 응답 헤더 만들기 (Spring 코드)
	    HttpHeaders responseHeader = new HttpHeaders();
	    responseHeader.setContentType(MediaType.APPLICATION_OCTET_STREAM);
	    responseHeader.setContentDisposition(ContentDisposition
	                                          .attachment()
	                                          .filename(originName)
	                                          .build());
	    responseHeader.setContentLength(file.length());
	    
	    // 응답
	    return new ResponseEntity<Resource>(resource, responseHeader, HttpStatus.OK);
	    
	  }
	  

	@Override
	public ResponseEntity<Resource> downloadAll(int boardNo) {
		String tempPath = myFileUtil.getTempPath();
	    File dir = new File(tempPath);
	    if(dir.exists() == false) {
	      dir.mkdirs();
	    }
	    
	    // zip 파일의 이름
	    String tempFileName = myFileUtil.getTempFileName();
	    
	    // zip 파일의 File 객체
	    File zFile = new File(tempPath, tempFileName);
	    
	    // zip 파일을 생성하기 위한 Java IO Stream 선언
	    BufferedInputStream bin = null;   // 각 첨부 파일을 읽어 들이는 스트림
	    ZipOutputStream zout = null;      // zip 파일을 만드는 스트림
	    
	    // 다운로드할 첨부 파일들의 정보(경로, 원래 이름, 저장된 이름) 가져오기
	    List<BoardFileDTO> boardFileList = boardMapper.getBoardFileList(boardNo);
	    
	    try {
	      
	      // ZipOutputStream 객체 생성
	      zout = new ZipOutputStream(new FileOutputStream(zFile));
	      
	      // 첨부 파일들을 하나씩 순회하면서 읽어들인 뒤 zip 파일에 추가하기 + 각 첨부 파일들의 다운로드 횟수 증가
	      for(BoardFileDTO boardFileDTO : boardFileList) {
	        
	        // zip 파일에 추가할 첨부 파일 이름 등록(첨부 파일의 원래 이름)
	        ZipEntry zipEntry = new ZipEntry(boardFileDTO.getBoardFileOriginName());
	        zout.putNextEntry(zipEntry);
	        
	        // zip 파일에 첨부 파일 추가
	        bin = new BufferedInputStream(new FileInputStream(new File(boardFileDTO.getBoardFilePath(), boardFileDTO.getBoardFileSystemName())));
	        
	        // bin -> zout으로 파일 복사하기 (Java 코드) <=> Spring의 Filecopy는 close의 문제로 사용 못 한다.
	        byte[] b = new byte[1024];   // 첨부 파일을 1KB 단위로 읽겠다.
	        int readByte = 0;            // 실제로 읽어들인 바이트 수
	        while((readByte = bin.read(b)) != -1) {
	          zout.write(b, 0, readByte);   // 바이트배열, 인덱스0부터, readByte(읽어들인 바이트 수 만큼)만큼만 보내시오
	                          // 읽어들인 내용은 바이트배열b에 저장. 0부터 readByte만큼 읽는다
	        }
	        bin.close();
	        zout.closeEntry();
	        
	      }
	      
	      zout.close();
	      
	    } catch(Exception e) {
	      e.printStackTrace();
	    }
	    
	    // 다운로드할 zip 파일의 File 객체 -> Resource 객체
	    Resource resource = new FileSystemResource(zFile);
	    
	    // 다운로드 응답 헤더 만들기 (Spring 코드)
	    HttpHeaders responseHeader = new HttpHeaders();
	    responseHeader.setContentType(MediaType.APPLICATION_OCTET_STREAM);
	    responseHeader.setContentDisposition(ContentDisposition
	                        .attachment()
	                        .filename(tempFileName)
	                        .build());
	    responseHeader.setContentLength(zFile.length());
	    
	    // 응답
	    return new ResponseEntity<Resource>(resource, responseHeader, HttpStatus.OK);
	  }

	@Override
	public MemberDTO goWrtie(HttpSession session, Model model) {
		MemberDTO member = new MemberDTO();
	    member = (MemberDTO) session.getAttribute("loginMember");
	    String emailId = member.getEmailId();
	    System.out.println(boardMapper.getMemberByEmail(emailId));
	    return boardMapper.getMemberByEmail(emailId);
	  }
	
	@Transactional
	@Override
	public int addBoard(MultipartHttpServletRequest request) {
		// 제목, 작성자, 내용 파라미터
	    String boardTitle = request.getParameter("boardTitle");
	    String emailId = request.getParameter("emailId");
	    int memberNo = boardMapper.getMemberByEmail(emailId).getMemberNo();
	    String boardContent = request.getParameter("boardContent");
	    
	    // DB로 보낼 NoticeDTO 만들기
	    MemberDTO memberDTO = new MemberDTO();
	    memberDTO.setMemberNo(memberNo);;
	    BoardDTO boardDTO = new BoardDTO();
	    boardDTO.setBoardTitle(boardTitle);
	    boardDTO.setMemberDTO(memberDTO);
	    boardDTO.setBoardContent(boardContent);
	    
	    // DB로 NoticeDTO 보내기 (삽입)
	    int addResult = boardMapper.addBoard(boardDTO);
	    
	    /* Notice_File_T 테이블에 NoticeFileDTO 넣기 */
	    
	    // 첨부된 파일 목록
	    List<MultipartFile> files = request.getFiles("files");  // <input type="file" name="files">
	    
	    // 첨부가 없는 경우에도 files 리스트는 비어 있지 않고,
	    // [MultipartFile[field="files", filename=, contentType=application/octet-stream, size=0]] 형식으로 MultipartFile을 하나 가진 것으로 처리된다.
	    
	    // 첨부된 파일 목록 순회
	    for(MultipartFile multipartFile : files) {
	      
	      // 첨부된 파일이 있는지 체크
	      if(multipartFile != null && multipartFile.isEmpty() == false) {
	        
	        // 예외 처리
	        try {
	          /* HDD에 첨부 파일 저장하기 */
	          
	          // 첨부 파일의 저장 경로
	          String path = myFileUtil.getPath();
	          // 첨부 파일의 저장 경로가 없으면 만들기
	          File dir = new File(path);
	          if(dir.exists() == false) {
	            dir.mkdirs();
	          }
	          
	          // 첨부 파일의 원래 이름
	          String originName = multipartFile.getOriginalFilename();
	          originName = originName.substring(originName.lastIndexOf("\\") + 1);  // IE는 전체 경로가 오기 때문에 마지막 역슬래시 뒤에 있는 파일명만 사용한다.
	          
	          // 첨부 파일의 저장 이름
	          String filesystemName = myFileUtil.getFilesystemName(originName);
	          
	          // 첨부 파일의 File 객체 (HDD에 저장할 첨부 파일)
	          File file = new File(dir, filesystemName);
	          
	          Path savePath = Paths.get(path);
	          
	          // 첨부 파일을 HDD에 저장
	          //FileCopyUtils.copy(multipartFile.getInputStream(), new FileOutputStream(savePath.toFile()));
	          multipartFile.transferTo(file);  // 실제로 서버에 저장된다.
	          
	          /* 썸네일(첨부 파일이 이미지인 경우에만 썸네일이 가능) */
	          
	          /* DB에 첨부 파일 정보 저장하기 */
	          
	          // DB로 보낼 NoticeFileDTO 만들기
	          BoardFileDTO boardFileDTO = new BoardFileDTO();
	          boardFileDTO.setBoardFileOriginName(originName);
	          boardFileDTO.setBoardFileSystemName(filesystemName);
	          boardFileDTO.setBoardFilePath(path);
	          boardFileDTO.setBoardDTO(boardDTO);
	          
	          // DB로 AttachDTO 보내기
	          boardMapper.addBoardFile(boardFileDTO);
	          
	        } catch(Exception e) {
	          e.printStackTrace();
	        }
	        
	      }
	      
	    }
	    
	    return addResult;
	    
	  }

	@Override
	public int removeBoard(int boardNo) {
		// 삭제할 첨부 파일들의 정보
	    List<BoardFileDTO> boardFileList = boardMapper.getBoardFileList(boardNo);
	    
	    // 첨부 파일이 있으면 삭제
	    if(boardFileList != null && boardFileList.isEmpty() == false) {
	      
	      // 삭제할 첨부 파일들을 순회하면서 하나씩 삭제
	      for(BoardFileDTO boardFileDTO : boardFileList) {
	        
	        // 삭제할 첨부 파일의 File 객체
	        File file = new File(boardFileDTO.getBoardFilePath(), boardFileDTO.getBoardFileSystemName());
	        
	        // 첨부 파일 삭제
	        if(file.exists()) {
	          file.delete();
	        }
	        
	      }
	      
	    }
	    
	    int removeResult = boardMapper.removeBoard(boardNo);
	    
	    return removeResult;
	    
	  }
	@Transactional
	@Override
	public int modifyBoard(MultipartHttpServletRequest request) {
	    
	    
	    // 제목, 내용, 업로드번호 파라미터
	    String boardTitle = request.getParameter("boardTitle");
	    String boardContent = request.getParameter("boardContent");
	    int boardNo = Integer.parseInt(request.getParameter("boardNo"));
	    
	    // DB로 보낼 NoticeDTO 만들기
	    BoardDTO boardDTO = new BoardDTO();
	    boardDTO.setBoardTitle(boardTitle);
	    boardDTO.setBoardContent(boardContent);
	    boardDTO.setBoardNo(boardNo);
	    
	    // DB로 UploadDTO 보내기
	    int modifyResult = boardMapper.modifyBoard(boardDTO);
	    
	    /* Attach 테이블에 AttachDTO 넣기 */
	    
	    // 첨부된 파일 목록
	    List<MultipartFile> files = request.getFiles("files");  // <input type="file" name="files">

	    // 첨부가 없는 경우에도 files 리스트는 비어 있지 않고,
	    // [MultipartFile[field="files", filename=, contentType=application/octet-stream, size=0]] 형식으로 MultipartFile을 하나 가진 것으로 처리된다.
	    
	    // 첨부된 파일 목록 순회
	    for(MultipartFile multipartFile : files) {
	      
	      // 첨부된 파일이 있는지 체크
	      if(multipartFile != null && multipartFile.isEmpty() == false) {
	        
	        // 예외 처리
	        try {
	          /* HDD에 첨부 파일 저장하기 */
	          
	          // 첨부 파일의 저장 경로
	          String path = "C:" + File.separator + "storage" + File.separator + "final";
	          System.out.println(path);
	          // 첨부 파일의 저장 경로가 없으면 만들기
	          File dir = new File(path);
	          if(dir.exists() == false) {
	            dir.mkdirs();
	          }
	          
	          // 첨부 파일의 원래 이름
	          String originName = multipartFile.getOriginalFilename();
	          originName = originName.substring(originName.lastIndexOf("\\") + 1);  // IE는 전체 경로가 오기 때문에 마지막 역슬래시 뒤에 있는 파일명만 사용한다.
	          
	          // 첨부 파일의 저장 이름
	          String filesystemName = myFileUtil.getFilesystemName(originName);
	          
	          // 첨부 파일의 File 객체 (HDD에 저장할 첨부 파일)
	          File file = new File(dir, filesystemName);
	          
	          Path savePath = Paths.get(path);
	          
	          // 첨부 파일을 HDD에 저장
	          //FileCopyUtils.copy(multipartFile.getInputStream(), new FileOutputStream(savePath.toFile()));
	          multipartFile.transferTo(file);  // 실제로 서버에 저장된다.
	          
	          /* 썸네일(첨부 파일이 이미지인 경우에만 썸네일이 가능) */
	          
	          /* DB에 첨부 파일 정보 저장하기 */
	          
	          // DB로 보낼 NoticeFileDTO 만들기
	          BoardFileDTO boardFileDTO = new BoardFileDTO();
	          boardFileDTO.setBoardFileOriginName(originName);
	          boardFileDTO.setBoardFileSystemName(filesystemName);
	          boardFileDTO.setBoardFilePath(path);
	          boardFileDTO.setBoardDTO(boardDTO);
	          
	          // DB로 AttachDTO 보내기
	          boardMapper.addBoardFile(boardFileDTO);
	          
	        } catch(Exception e) {
	          e.printStackTrace();
	        }
	        
	      }
	      
	    }
	    
	    return modifyResult;
	  }

	@Override
	public int removeBoardFile(int boardFileNo) {
		// 삭제할 첨부 파일의 정보 가져오기
	    BoardFileDTO boardFileDTO = boardMapper.getBoardFileByNo(boardFileNo);
	    
	    // 첨부 파일이 있으면 삭제
	    if(boardFileDTO != null) {
	      
	      // 삭제할 첨부 파일의 File 객체
	      File file = new File(boardFileDTO.getBoardFilePath(), boardFileDTO.getBoardFileSystemName());
	      
	      // 첨부 파일 삭제
	      if(file.exists()) {
	        file.delete();
	      }
	    }

	    // DB에서 noticeFileNo값을 가지는 NOTICE_FILE_T 테이블의 데이터를 삭제
	    int removeResult = boardMapper.removeBoardFile(boardFileNo);
	    
	    return removeResult;
	    
	  }
	  
	  
	}
