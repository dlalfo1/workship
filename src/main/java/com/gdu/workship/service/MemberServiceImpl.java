package com.gdu.workship.service;

import java.io.File;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Optional;
import java.util.UUID;
import java.util.regex.Matcher;

import javax.servlet.http.HttpServletRequest;

import org.springframework.stereotype.Service;
import org.springframework.ui.Model;
import org.springframework.web.multipart.MultipartFile;
import org.springframework.web.multipart.MultipartHttpServletRequest;

import com.gdu.workship.domain.DepartmentDTO;
import com.gdu.workship.domain.JobDTO;
import com.gdu.workship.domain.MemberDTO;
import com.gdu.workship.mapper.MemberMapper;
import com.gdu.workship.util.MyFileUtil;
import com.gdu.workship.util.PageUtil;

import lombok.RequiredArgsConstructor;

@RequiredArgsConstructor
@Service
public class MemberServiceImpl implements MemberService {

  private final MemberMapper memberMapper;
  private final PageUtil pageUtil;
  private final MyFileUtil myFileUtil;
  
  @Override
  public void loadMemberList(HttpServletRequest request, Model model) {

    // 파라미터 page가 전달되지 않는 경우 page=1로 처리한다.
    Optional<String> opt1 = Optional.ofNullable(request.getParameter("page"));
    int page = Integer.parseInt(opt1.orElse("1"));
    
    // 전체 레코드 개수를 구한다.
    int totalRecord = memberMapper.getMemberCount();
    
    int recordPerPage = 10;

    // 파라미터 column이 전달되지 않는 경우 column=MEMBER_NO로 처리한다.
    Optional<String> opt2 = Optional.ofNullable(request.getParameter("column"));
    String column = opt2.orElse("M.MEMBER_NO");
    
    // 파라미터 query가 전달되지 않는 경우 query=""로 처리한다. (query가 없으면 null값, 처음 화면이동 요청..)
    Optional<String> opt3 = Optional.ofNullable(request.getParameter("query"));
    String query = opt3.orElse("");
    
    
    // DB로 보낼 Map 만들기
    Map<String, Object> map = new HashMap<String, Object>();
                                                // LIMIT #{begin}, #{recordPerPage}
    map.put("begin", pageUtil.getBegin());      // begin은 0부터 시작한다. (PageUtil.java 참고)
    map.put("recordPerPage", recordPerPage);    // end 대신 recordPerPage를 전달한다.
    map.put("column", column);
    map.put("query", query);
    // PageUtil(Pagination에 필요한 모든 정보) 계산하기
    pageUtil.setPageUtil(page, (column.isEmpty() && query.isEmpty()) ? totalRecord : memberMapper.getMemberSearchCount(map), recordPerPage);
    System.out.println(totalRecord);
    // begin ~ end 사이의 목록 가져오기
    List<MemberDTO> memberList = memberMapper.getMemberList(map);
    List<DepartmentDTO> deptList = memberMapper.getDeptList();
    System.out.println(memberList);
    // pagination.jsp로 전달할(forward)할 정보 저장하기
    model.addAttribute("memberList", memberList);
    model.addAttribute("deptList", deptList);
    if(column.isEmpty() || query.isEmpty()) {
      model.addAttribute("pagination", pageUtil.getPagination(request.getContextPath() + "/member/memberList.do"));
    } else {
      model.addAttribute("pagination", pageUtil.getPagination(request.getContextPath() + "/member/memberList.do?column=" + column + "&query=" + query));
    }
  }
  
  @Override
  public Map<String, Object> loadMemberList2(HttpServletRequest request) {

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
    List<DepartmentDTO> deptList = memberMapper.getDeptList();
    
    Map<String, Object> result = new HashMap<String, Object>();
    // pagination.jsp로 전달할(forward)할 정보 저장하기
    result.put("memberList", memberList);
    result.put("deptList", deptList);
    result.put("pagination", pageUtil.getPagination(request.getContextPath() + "/member/memberList.do?column=" + column + "&order=" + order));
    switch(order) {
    case "ASC" : result.put("order", "DESC"); break;  // 현재 ASC 정렬이므로 다음 정렬은 DESC이라고 Jsp에 알려준다.
    case "DESC": result.put("order", "ASC"); break;
    }
    
    return result;
  }
  
  @Override
  public int addMember(MultipartHttpServletRequest request) {
    
    String emailId = request.getParameter("emailId");
    String memberName = request.getParameter("memberName");
    String Tel = request.getParameter("Tel");
    String birthday = request.getParameter("birthday");
    int deptNo = request.getParameter("deptNo").isEmpty() ? 6 : Integer.parseInt(request.getParameter("deptNo"));
    int jobNo = request.getParameter("jobNo").isEmpty() ? 1 : Integer.parseInt(request.getParameter("jobNo"));
    String pw = request.getParameter("pw");
    
    String deptName = memberMapper.selectDept(deptNo).getDeptName();
    String jobName = memberMapper.selectJob(jobNo).getJobName();
    
    DepartmentDTO dept = new DepartmentDTO();
    dept.setDeptNo(deptNo);
    dept.setDeptName(deptName);
    JobDTO job = new JobDTO();
    job.setJobNo(jobNo);
    job.setJobName(jobName);
    
    MemberDTO member = new MemberDTO();
    member.setEmailId(emailId);
    member.setMemberName(memberName);
    member.setTel(Tel);
    member.setBirthday(birthday);
    member.setPw(pw);
    member.setDepartmentDTO(dept);
    member.setJobDTO(job);
    
    String sep = Matcher.quoteReplacement(File.separator);
    
    MultipartFile file = request.getFile("attachImage");
    if(file != null && file.isEmpty() == false) {
      
      try {
        String path = "/storage" + sep + "final";
        
        File dir = new File(path);
        if(dir.exists() == false) {
          dir.mkdirs();
        }
        
        String extName = null;
        
        String originName = file.getOriginalFilename();
        
        if(originName.endsWith("tar.gz")) {
          extName = "tar.gz";
        } else {
          String[] array = originName.split("\\.");
          extName = array[array.length - 1];  // 배열의 마지막 인덱스
        }
        System.out.println(path);
        System.out.println(originName);
        String fileName = UUID.randomUUID().toString().replace("-", "") + "." + extName;
        System.out.println(fileName);

        File savefile = new File(dir, fileName);
        
        file.transferTo(savefile); 
        
        member.setProfileFilePath(path);
        member.setProfileFileName(fileName);
        
      } catch(Exception e) {
        e.printStackTrace();
        
        return 0;
      }
      
    }
    
    int addResult = memberMapper.createMember(member);
    
    return addResult;
    
  }
  

}
