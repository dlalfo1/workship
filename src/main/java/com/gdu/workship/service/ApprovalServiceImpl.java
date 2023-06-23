package com.gdu.workship.service;

import java.io.File;
import java.net.URLEncoder;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Optional;

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
import org.springframework.web.servlet.mvc.support.RedirectAttributes;

import com.gdu.workship.domain.ApprovalDTO;
import com.gdu.workship.domain.ApprovalFileDTO;
import com.gdu.workship.domain.ApprovalLineDTO;
import com.gdu.workship.domain.DepartmentDTO;
import com.gdu.workship.domain.MemberDTO;
import com.gdu.workship.mapper.ApprovalMapper;
import com.gdu.workship.util.MyFileUtil;
import com.gdu.workship.util.PageUtil3;

import lombok.RequiredArgsConstructor;

@RequiredArgsConstructor
@Service
public class ApprovalServiceImpl implements ApprovalService {
  
  private final ApprovalMapper approvalMapper;
  private final PageUtil3 pageUtil;
  private final MyFileUtil myFileUtil;
  
  @Override
  public List<DepartmentDTO> getDeptList() {
    
    return approvalMapper.selectDeptList();
  } 
  
  @Override
  public Map<String, Object> getMemberList(HttpServletRequest request) {
   
    Optional<String> opt = Optional.ofNullable(request.getParameter("deptNo"));
    int deptNo = Integer.parseInt(opt.orElse("1"));
   
    Map<String, Object> map = new HashMap<>();
    map.put("memberList", approvalMapper.selectMemberList(deptNo));
   
    return map;
  } 
  
  @Override
  public Map<String, Object> getInsertCheckMemberList(Map<String, Object> map) {
    
   // ajax요청에 반환할 맵
   Map<String, Object> map1 = new HashMap<>();
   map1.put("checkMembers", approvalMapper.selectInsertCheckMemberList(map));
    
   return map1;
  }
  
  @Override
  public Map<String, Object> getApprovalAndReferenceMemSberList(Map<String, Object> map) {
    
  /*
    // map에 있는 배열 꺼내기
    // 키(key)와 값(value)이 모두 필요한 경우 사용한다.
    //  keySet() 메서드는 key의 값만 필요한 경우 사용한다.
    for (Map.Entry<String, Object> entry : map.entrySet()) {
      String key = entry.getKey();
      Object value = entry.getValue();
      
      // 키(key)와 값(value)에 접근하여 원하는 작업 수행
      System.out.println("Key: " + key + ", Value: " + value);
    }
   */
    
    // DB로 보낼 Map 만들기
    Map<String, Object> map1 = new HashMap<String, Object>();
    map1.put("approvalMembers", map.get("approvalMembers"));

    Map<String, Object> map2 = new HashMap<String, Object>();
    map2.put("referenceMembers", map.get("referenceMembers"));

    if(map.containsKey("referenceMembers")) {
      Map<String, Object> map3 = new HashMap<String, Object>(); 
      map3.put("approvalMembers", approvalMapper.selectApprovalMmberList(map1));
      map3.put("referenceMembers", approvalMapper.selectReferenceMmberList(map2));
      return map3;
    } else {
      Map<String, Object> map3 = new HashMap<String, Object>(); 
      map3.put("approvalMembers", approvalMapper.selectApprovalMmberList(map1));
      return map3;
    }
   
  } 
  
  @Override
  @Transactional
  public int addApproval(MultipartHttpServletRequest multipartRequest, RedirectAttributes redirectAttributes) {
    
    int memberNo = Integer.parseInt(multipartRequest.getParameter("memberNo"));                          // 사원번호
    int docName = Integer.parseInt(multipartRequest.getParameter("formNo"));                             // 기안이름
    String docTitle = multipartRequest.getParameter("title");                                            // 기안제목
    String docContent = multipartRequest.getParameter("content");                                        // 기안 내용
    int docStatus = Integer.parseInt(multipartRequest.getParameter("docStatus"));                        // 문서상태
    int approvalStatus = Integer.parseInt(multipartRequest.getParameter("approvalStatus"));              // 기안진행상태
    int approvalCount = Integer.parseInt(multipartRequest.getParameter("approvalCount"));                // 총 결재자수
    int approvalSequence = Integer.parseInt(multipartRequest.getParameter("approvalSequence"));          // 총 현재결재순서
    int memberApprovalStatus = Integer.parseInt(multipartRequest.getParameter("memberApprovalStatus"));  // 총 현재결재순서
    int vacationState = Integer.parseInt(multipartRequest.getParameter("vacationState"));
    
    String vacationCategory = multipartRequest.getParameter("vacationCategory");
    
    SimpleDateFormat dateFormat = new SimpleDateFormat("yyyy-MM-dd");
    
    String startDate = multipartRequest.getParameter("startDate");
    String endDate = multipartRequest.getParameter("endDate");
    String strPayDate = multipartRequest.getParameter("payDate");
    String strResignationDate = multipartRequest.getParameter("resignationDate");
    
    System.out.println("지출일자 : " + strPayDate);

    Date vacationStartDate = null;
    Date vacationEndDate = null;
    Date payDate = null;
    Date resignationDate = null;
   
    try {
           if(startDate != null) {
             vacationStartDate = dateFormat.parse(startDate);
           }
           if(endDate != null) {
             vacationEndDate = dateFormat.parse(endDate);
           }
           if(strPayDate != null) {
             payDate = dateFormat.parse(strPayDate);
           }
           if(strResignationDate != null) {
             resignationDate = dateFormat.parse(strResignationDate);
           }
    } catch (Exception e) {
      e.printStackTrace();
    }
   
    // insertApproval에 전달할 approvalDTO
    ApprovalDTO approvalDTO = new ApprovalDTO();
    MemberDTO memberDTO = new MemberDTO();
    memberDTO.setMemberNo(memberNo);
    
    approvalDTO.setMemberDTO(memberDTO);
    approvalDTO.setDocName(docName);
    approvalDTO.setDocTitle(docTitle);
    approvalDTO.setDocContent(docContent);
    approvalDTO.setDocStatus(docStatus);  
    approvalDTO.setApprovalStatus(approvalStatus);
    approvalDTO.setApprovalCount(approvalCount);
    approvalDTO.setApprovalSequence(approvalSequence);
    approvalDTO.setVacationCategory(vacationCategory);
    approvalDTO.setVacationStartDate(vacationStartDate);
    approvalDTO.setVacationEndDate(vacationEndDate);
    approvalDTO.setPayDate(payDate);
    approvalDTO.setResignationDate(resignationDate);
    approvalDTO.setVacationState(vacationState);
    
    int addApprovalResult = approvalMapper.insertApproval(approvalDTO);
    

    // insertApprovalLine에 전달할 Map
    String[] approvalMemberNos =   multipartRequest.getParameterValues("approvalMemberNo"); 
    String[] approvalOrders = multipartRequest.getParameterValues("approvalOrder"); 
    
    int addpprovalLineResult = 0;
    
    Map<String, Object> map1 = new HashMap<>();
    
    for(int i=0; i < approvalMemberNos.length; i++) {
      map1.put("approvalMemberNo", approvalMemberNos[i]);
      map1.put("approvalOrder", approvalOrders[i]);
      map1.put("memberApprovalStatus", memberApprovalStatus);
      map1.put("approvalNo", approvalDTO.getApprovalNo());
      addpprovalLineResult = approvalMapper.insertApprovalLine(map1);
    }
    


    // insertReferenceLine에 전달할 Map
    String[] referenceMemberNos =   multipartRequest.getParameterValues("referenceMemberNo"); 
    int addReferenceLineResult = 0;
    
    if(referenceMemberNos != null) {

     
      Map<String, Object> map2 = new HashMap<>();
      
      for(int i = 0; i < referenceMemberNos.length; i ++) {
        map2.put("referenceMemberNo", referenceMemberNos[i]);
        map2.put("approvalNo", approvalDTO.getApprovalNo()); // <selectKey> 사용
        addReferenceLineResult = approvalMapper.insertReferenceLine(map2);
      }

    }
    
    // insertApprovalAttach에 전달할 ApprovalFileDTO
    List<MultipartFile> files = multipartRequest.getFiles("files");
    int addApprovalFileResult = 0;
    
    for(MultipartFile multipartFile : files) {
      
      // 첨부파일이 있을시 실행
      if(multipartFile != null && multipartFile.isEmpty() == false) {
        
        try {
                
              // 첨부 파일의 저장 경로
              String approvalFilePath = myFileUtil.getPath();
              
              // 첨부 파일의 저장 경로가 없으면 만들기
              File dir = new File(approvalFilePath);
              if(dir.exists() == false) {
                dir.mkdirs();
              }
              
              // 첨부 파일의 원래 이름
              String approvalFileOriginName = multipartFile.getOriginalFilename();
              approvalFileOriginName = approvalFileOriginName.substring(approvalFileOriginName.lastIndexOf("\\") + 1); 
               
              // 첨부 파일의 저장 이름
              String approvalFileSystemName = myFileUtil.getFilesystemName(approvalFileOriginName);
              
              // 첨부 파일의 File 객체 (HDD(하드디스크)에 저장할 첨부 파일)
              File file = new File(dir, approvalFileSystemName);
              
              // 첨부 파일을 HDD에 저장
              multipartFile.transferTo(file);
              
              /* DB에 첨부파일 정보 저장하기 */
              ApprovalFileDTO approvalFileDTO = new ApprovalFileDTO();
              approvalFileDTO.setApprovalFileOriginName(approvalFileOriginName);
              approvalFileDTO.setApprovalFileSystemName(approvalFileSystemName);
              approvalFileDTO.setApprovalFilePath(approvalFilePath);
              approvalFileDTO.setApprovalDTO(approvalDTO);
              
              addApprovalFileResult = approvalMapper.insertApprovalAttach(approvalFileDTO);
              
              
        } catch(Exception e) {
          e.printStackTrace();
        }
      }
    }
    
    return addApprovalResult + addpprovalLineResult + addReferenceLineResult + addApprovalFileResult;
  }

  
  @Override
  public ApprovalDTO detailApprovalByNo(HttpServletRequest request) {
    
    int approvalNo = Integer.parseInt(request.getParameter("approvalNo"));
    int docName = Integer.parseInt(request.getParameter("docName"));
    
    // selectApprovalByNo에 보낼 ApprovalDTO
    ApprovalDTO approvalDTO = new ApprovalDTO();
    approvalDTO.setApprovalNo(approvalNo);
    approvalDTO.setDocName(docName);

    
    return approvalMapper.selectApprovalByNo(approvalDTO);  
  }
  
  @Override
  public List<ApprovalLineDTO> getApprovalLine(HttpServletRequest request) {
    
    int approvalNo = Integer.parseInt(request.getParameter("approvalNo"));
    
    return approvalMapper.selectApprovalLineMembers(approvalNo);
  }
  
  @Override
  public List<ApprovalFileDTO> detailApprovalFilesByNo(HttpServletRequest request) {
    
    int approvalNo = Integer.parseInt(request.getParameter("approvalNo"));
    
    return approvalMapper.selectApprovalFilesByApprovalNo(approvalNo);
  }
  
  @Override
  public ResponseEntity<Resource> download(int attachNo, String userAgent) {
    
    // 다운로드 할 첨부 파일의 정보(경로, 원래 이름, 저장된 이름) 가져오기
    ApprovalFileDTO approvalFileDTO = approvalMapper.selectApprovalFilesByAttachNo(attachNo);
    
    // 다운로드 할 첨부 파일의 File 객체 -> Resource 객체
    File file = new File(approvalFileDTO.getApprovalFilePath(), approvalFileDTO.getApprovalFileSystemName());
    Resource resource = new FileSystemResource(file);
    
    // 다운로드 할 첨부 파일의 존재 여부 확인(다운로드 실패를 반환)
    if(resource.exists() == false) {
      return new ResponseEntity<Resource>(HttpStatus.NOT_FOUND);
    }
    
    // 다운로드 횟수 증가하기
   // uploadMapper.increaseDownloadCount(attachNo);
    
    
    // 다운로드 되는 파일명(첨부 파일의 원래 이름, UserAgent(브라우저)에 따른 인코딩 세팅)
    String originName = approvalFileDTO.getApprovalFileOriginName();
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
  
    // 다운로드 응답 헤더 만들기 (Jsp/Servlet 코드)
    /*
    MultiValueMap<String, String> responseHeader = new HttpHeaders();
    responseHeader.add("Content-Type", "application/octet-stream");
    responseHeader.add("Content-Disposition", "attachment; filename=" + originName);
    responseHeader.add("Content-Length", file.length() + "");
    */
  
    // 다운로드 응답 헤더 만들기 (Spring 코드)
    HttpHeaders responseHeader = new HttpHeaders();
    responseHeader.setContentType(MediaType.APPLICATION_OCTET_STREAM);
    responseHeader.setContentDisposition(ContentDisposition.attachment().filename(originName).build());
    responseHeader.setContentLength(file.length());
  
    // 응답
    return new ResponseEntity<Resource>(resource, responseHeader, HttpStatus.OK);
  
  }
  
  @Override
  public void getApprovalList(HttpServletRequest request, Model model, HttpSession session) {
      
    // 파라미터 page가 전달되지 않은 경우 page=1로 처리한다.
    Optional<String> opt1 = Optional.ofNullable(request.getParameter("page"));
    int page = Integer.parseInt(opt1.orElse("1"));
  
    // 세션에 있는 recordPerPage를 가져온다. 세션에 없는 경우(첫 목록 - 1페이지 뿌릴 때) recordPerPage를=10으로 처리한다.
    //  HttpSession session = request.getSession();
    Optional<Object> opt2 = Optional.ofNullable(session.getAttribute("recordPerPage"));
    int recordPerPage = (int)(opt2.orElse(10)); 
    
    // 파라미터 order가 전달되지 않은 경우 order=ASC로 처리한다.
    Optional<String> opt3 = Optional.ofNullable(request.getParameter("order"));
    String order = opt3.orElse("ASC");
    
    // 파라미터 column이 전달되지 않은 경우 cloumn=APPROVAL_NO로 처리한다.
    Optional<String> opt4 = Optional.ofNullable(request.getParameter("columnorder"));
    String columnorder = opt4.orElse("APPROVAL_NO");
    
    // 파라미터 query이 전달되지 않은 경우 query=""로 처리한다.
    Optional<String> opt5 = Optional.ofNullable(request.getParameter("query"));
    String query = opt5.orElse("");
    
    // 검색시 받는 정렬 칼럼
    Optional<String> opt6 = Optional.ofNullable(request.getParameter("columnsearch"));
    String columnsearch = opt6.orElse("DOC_TITLE");
    
     // 파라미터 approvalStatus가 전달되지 않은 경우 approvalStatus=5로 처리한다. (5는 없음)
     Optional<String> opt7 = Optional.ofNullable(request.getParameter("approvalStatus"));
     int approvalStatus = Integer.parseInt(opt7.orElse("5")); // 전체보기   
    
     
     
    Map<String, Object> map1 = new HashMap<String, Object>();
    map1.put("query", query);
    map1.put("columnsearch", columnsearch);

    int totalRecord = 0;
    
    Map<String, Object> map3 = new HashMap<>();
    map3.put("approvalStatus", approvalStatus);
    if(approvalStatus == 5) { 
      totalRecord = approvalMapper.getApprovalCountByQuery(map1);
    }
    
    if(approvalStatus != 5 ) {
      totalRecord= approvalMapper.getApprovalCountApprovalStatus(map3);
    } 
    // 'recordPerPage' 값이 변경되었을 때, 현재 페이지의 데이터가 없는 경우를 확인합니다.
    int totalPage = (int) Math.ceil((double) totalRecord / recordPerPage);

    if ((page - 1) * recordPerPage >= totalRecord) {
        page = Math.max(totalPage, 1);
    }
    
    // 검색에 따라 
    // PageUtil(Pagination에 필요한 모든 정보 계산하기
    pageUtil.setPageUtil(page, totalRecord, recordPerPage);
    
    // 세션에 있는 로그인 정보 가져오기
    MemberDTO memberDTO = (MemberDTO)session.getAttribute("loginMember");
    session.getAttribute("loginMember");
    
    // DB로 보낼 Map 만들기
    Map<String, Object> map2 = new HashMap<String, Object>();
    map2.put("begin", pageUtil.getBegin() -1);
    map2.put("recordPerPage", recordPerPage);
    map2.put("columnorder", columnorder);
    map2.put("columnsearch", columnsearch);
    map2.put("query", query);
    map2.put("order", order);
    map2.put("approvalStatus", approvalStatus);
    map2.put("memberNo", memberDTO.getMemberNo());
    
    // 전체 목록 가져오기
    List<ApprovalDTO> approvalList = approvalMapper.getApprovalList(map2);
    
    model.addAttribute("approvalList", approvalList); 

    // order의 값을 알고 있는 서비스에서 전달해주기.
    // column과 query 파라미터를 넘겨줘야 페이지가 바뀌어도 검색한 문자가 유지된다.
    model.addAttribute("pagination", pageUtil.getPagination(request.getContextPath() + "/approval/approvalList.do?columnorder=" +  columnorder + "&columnsearch=" +  columnsearch + "&order=" + order + "&query=" + query + "&approvalStatus=" + approvalStatus));
    model.addAttribute("beginNo", totalRecord - (page - 1) * recordPerPage);
    model.addAttribute("columnorder", columnorder);
    model.addAttribute("columnsearch", columnsearch);
    
    switch(order) {
    case "ASC" : model.addAttribute("order", "DESC"); break;  
    case "DESC" : model.addAttribute("order", "ASC"); break; 
    }
    
    model.addAttribute("page", page);
    model.addAttribute("query", query);
    
    // 마지막 페이지로 이동하는 경우 파라미터 값도 같이 변경합니다.
    if (page > totalPage) {
        // 마지막 페이지로 설정합니다.
        page = totalPage;
        // 파라미터 값을 변경합니다.
        map2.put("begin", pageUtil.getBegin() -1);
    }
    
  }
  
  @Override
  public Map<String, Object> getAutoComplete(HttpServletRequest request) {
    
    // 파라미터 column이 전달되지 않는 경우 column=""로 처리한다. (검색할 칼럼)
    Optional<String> opt1 = Optional.ofNullable(request.getParameter("columnsearch"));
    String column = opt1.orElse("");
    
    // 파라미터 query가 전달되지 않는 경우 query=""로 처리한다. (검색어)
    Optional<String> opt2 = Optional.ofNullable(request.getParameter("query"));
    String query = opt2.orElse("");
    
    // DB로 보낼 Map 만들기(column + query)
    Map<String, Object> map = new HashMap<String, Object>();
    map.put("column", column);
    map.put("query", query);
    
    // 검색 결과 목록 가져오기
    List<ApprovalDTO> approvals = approvalMapper.getApprovalAutoComplete(map);
    
    Map<String, Object> resultMap = new HashMap<String, Object>();
    resultMap.put("approvals", approvals);

    return resultMap;
  }
}
   
  


