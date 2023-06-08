package com.gdu.workship.service;

import java.io.File;
import java.nio.file.Files;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Optional;
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
        
        String fileName = file.getOriginalFilename();
        
        File savefile = new File(dir, fileName);
        
        file.transferTo(savefile); 
        
        // 썸네일(첨부 파일이 이미지인 경우에만 썸네일이 가능)
        // originName,filesystemName 을 활용하지 않고 파일의 확장자를 구하는 다른 방법 (원래이름,저장이름을 쓰면 코드가 중복된다)
        
        // 첨부 파일의 Content-Type 확인
        /*String contentType = Files.probeContentType(savefile.toPath());  // probeContentType은 path를 매개변수로 받는다.(file.toPath()메소드로 쉽게 처리가능)
                                       // 이미지 파일의 Content-Type : image/jpeg, image/png, image/gif, ... (image로 시작하느냐를 체크하면 되겠다)
        
        // DB에 저장할 썸네일 유무 정보 처리
        boolean hasThumbnail = contentType != null && contentType.startsWith("image"); // 초기값은 false로 잡아준다(있는 지 없는 지 모르니까)
        
        // 첨부 파일의 Content-Type이 이미지로 확인되면 썸네일로 만듬
        if(hasThumbnail) {
          
          // HDD에 썸네일 저장하기 (thumbnailator 디펜던시)
          File thumbnail = new File(dir, "s_" + fileName);   // toFile() 우리가 어떻게 저장할 지를 약속을 정해서 하면 된다.(s_ 붙인다) 
          Thumbnails.of(file)
              .size(50, 50)
              .toFile(thumbnail);
        }
        */
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
