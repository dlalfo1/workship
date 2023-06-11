package com.gdu.workship.service;

import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Optional;

import javax.servlet.http.HttpServletRequest;

import org.springframework.stereotype.Service;
import org.springframework.ui.Model;

import com.gdu.workship.domain.NoticeDTO;
import com.gdu.workship.mapper.NoticeBoardMapper;
import com.gdu.workship.util.PageUtil;

import lombok.RequiredArgsConstructor;

@RequiredArgsConstructor
@Service
public class NoticeBoardServiceImpl implements NoticeBoardService {

  private final NoticeBoardMapper noticeBoardMapper;
  private final PageUtil pageUtil;
  
  @Override
  public void loadNoticeBoardList(HttpServletRequest request, Model model) {

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
    
    List<NoticeDTO> noticeList = noticeBoardMapper.getNoticeList(map);
    System.out.println(noticeList);
    model.addAttribute("noticeList", noticeList);
    model.addAttribute("beginNo", totalRecord - (page - 1) * recordPerPage);
    if(column.isEmpty() || query.isEmpty()) {
      model.addAttribute("pagination", pageUtil.getPagination(request.getContextPath() + "/notice/noticeList.do"));
    } else {
      model.addAttribute("pagination", pageUtil.getPagination(request.getContextPath() + "/notice/noticeList.do?column=" + column + "&query=" + query));
    }
  }

  /*
  @Override
  public void getNoticeListUsingSearch(HttpServletRequest request, Model model) {
    // 파라미터 column이 전달되지 않는 경우 column=""로 처리한다. (column이 없으면 null값, 처음 화면이동 요청..)
    Optional<String> opt1 = Optional.ofNullable(request.getParameter("column"));
    String column = opt1.orElse("");
    
    // 파라미터 query가 전달되지 않는 경우 query=""로 처리한다. (query가 없으면 null값, 처음 화면이동 요청..)
    Optional<String> opt2 = Optional.ofNullable(request.getParameter("query"));
    String query = opt2.orElse("");
    System.out.println(column + ", " + query);
    // DB로 보낼 Map에 column,query 추가하기
    Map<String, Object> map = new HashMap<String, Object>();
    map.put("column", column);
    map.put("query", query);
    
    // 파라미터 page가 전달되지 않는 경우 page=1 로 처리한다.
    Optional<String> opt3 = Optional.ofNullable(request.getParameter("page"));
    int page = Integer.parseInt(opt3.orElse("1"));
    
    // 검색된 레코드 개수를 구한다.(DB에서 구해온다 => 불러주기만 하면 끝남)
    int totalRecord = noticeBoardMapper.getNoticeSearchCount(map);
    
    // recordPerPage = 10으로 처리한다.
    int recordPerPage = 10;
    
    // PageUtil(Pagination에 필요한 모든 정보(9개 필드값)) 계산하기
    pageUtil.setPageUtil(page, totalRecord, recordPerPage);

    // DB로 보낼 Map에 begin,recordPerPage 추가하기
                                                 // LIMIT #{begin}, #{recordPerPage}
    map.put("begin", pageUtil.getBegin());       // begin은 0부터 시작한다. (PageUtil.java 참고)
    map.put("recordPerPage", recordPerPage);     // end 대신 recordPerPage를 전달한다. 

    // begin부터 recordPerPage개의 목록 가져오기
    List<NoticeDTO> employees = noticeBoardMapper.getNoticeListUsingSearch(map); 
    
    // search.jsp로 전달할(forward)할 정보를 저장하기
    model.addAttribute("employees", employees);
    model.addAttribute("pagination", pageUtil.getPagination(request.getContextPath() + "/notice/searchNotice.do?column=" + column + "&query=" + query));
    model.addAttribute("beginNo", totalRecord - (page - 1) * recordPerPage);
    
  }
  */
  
  @Override
  public void getNoticeByNo(int noticeNo, Model model) {
    model.addAttribute("n", noticeBoardMapper.getNoticeByNo(noticeNo));
    System.out.println(noticeBoardMapper.getNoticeByNo(noticeNo));
    model.addAttribute("attachList", noticeBoardMapper.getAttachList(noticeNo));
  }
  
}
