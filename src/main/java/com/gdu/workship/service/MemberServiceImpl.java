package com.gdu.workship.service;

import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Optional;

import javax.servlet.http.HttpServletRequest;

import org.springframework.stereotype.Service;
import org.springframework.ui.Model;

import com.gdu.workship.domain.MemberDTO;
import com.gdu.workship.mapper.MemberMapper;
import com.gdu.workship.util.PageUtil;

import lombok.RequiredArgsConstructor;

@RequiredArgsConstructor
@Service
public class MemberServiceImpl implements MemberService {

  private final MemberMapper memberMapper;
  private final PageUtil pageUtil;
  
  @Override
  public void loadMemberList(HttpServletRequest request, Model model) {

    // 파라미터 page가 전달되지 않는 경우 page=1로 처리한다.
    Optional<String> opt1 = Optional.ofNullable(request.getParameter("page"));
    int page = Integer.parseInt(opt1.orElse("1"));
    
    // 전체 레코드 개수를 구한다.
    int totalRecord = memberMapper.getMemberCount();
    
    int recordPerPage = 10;

    // 파라미터 order가 전달되지 않는 경우 order=ASC로 처리한다.
    Optional<String> opt3 = Optional.ofNullable(request.getParameter("order"));
    String order = opt3.orElse("ASC");

    // 파라미터 column이 전달되지 않는 경우 column=MEMBER_NO로 처리한다.
    Optional<String> opt4 = Optional.ofNullable(request.getParameter("column"));
    String column = opt4.orElse("MEMBER_NO");
    
    // PageUtil(Pagination에 필요한 모든 정보) 계산하기
    pageUtil.setPageUtil(page, totalRecord, recordPerPage);
    
    // DB로 보낼 Map 만들기
    Map<String, Object> map = new HashMap<String, Object>();
                                                // LIMIT #{begin}, #{recordPerPage}
    map.put("begin", pageUtil.getBegin());      // begin은 0부터 시작한다. (PageUtil.java 참고)
    map.put("recordPerPage", recordPerPage);    // end 대신 recordPerPage를 전달한다.
    map.put("order", order);
    map.put("column", column);
    
    // begin ~ end 사이의 목록 가져오기
    List<MemberDTO> memberList = memberMapper.getMemberList(map);
    
    // pagination.jsp로 전달할(forward)할 정보 저장하기
    model.addAttribute("memberList", memberList);
    model.addAttribute("pagination", pageUtil.getPagination(request.getContextPath() + "/member/memberList.do?column=" + column + "&order=" + order));
    switch(order) {
    case "ASC" : model.addAttribute("order", "DESC"); break;  // 현재 ASC 정렬이므로 다음 정렬은 DESC이라고 Jsp에 알려준다.
    case "DESC": model.addAttribute("order", "ASC"); break;
    }

  }

}
