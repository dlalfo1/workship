package com.gdu.workship.service;

import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Optional;

import javax.servlet.http.HttpServletRequest;

import org.springframework.stereotype.Service;
import org.springframework.ui.Model;
import org.springframework.web.multipart.MultipartHttpServletRequest;

import com.gdu.workship.domain.DepartmentDTO;
import com.gdu.workship.domain.IngDTO;
import com.gdu.workship.domain.MemberDTO;
import com.gdu.workship.domain.ProjectDTO;
import com.gdu.workship.domain.ProjectWorkDTO;
import com.gdu.workship.mapper.MemberMapper;
import com.gdu.workship.mapper.ProjectMapper;
import com.gdu.workship.util.MyFileUtil;
import com.gdu.workship.util.PageUtil;

import lombok.RequiredArgsConstructor;

@RequiredArgsConstructor
@Service
public class ProjectServiceImpl implements ProjectService {
	
	private final ProjectMapper projectMapper;
	private final MemberMapper memberMapper;
	private final MyFileUtil myFileUtil;
	private final PageUtil pageUtil;
	
	@Override
		public int addProjectM(HttpServletRequest request) {
			
			// 파라미터
			String projectTitle = request.getParameter("projectTitle");
			int memberNo = request.getParameter("memberNo").isEmpty() ? 1 : Integer.parseInt(request.getParameter("memberNo"));
			int deptNo = request.getParameter("deptNo").isEmpty() ? 1 : Integer.parseInt(request.getParameter("deptNo"));
			String projectStartAt = request.getParameter("projectStartAt");
			String projectEndAt = request.getParameter("projectEndAt");
			int ingNo = request.getParameter("ingNo").isEmpty() ? 1 : Integer.parseInt(request.getParameter("ingNo"));
			
			
			String deptName = memberMapper.selectDept(deptNo).getDeptName();
			String ingState = projectMapper.selectIng(ingNo).getIngState();

			IngDTO ing = new IngDTO();
			ing.setIngNo(ingNo);
			ing.setIngState(ingState);
			
			DepartmentDTO dept = new DepartmentDTO();
			dept.setDeptNo(deptNo);
			dept.setDeptName(deptName);
			
			MemberDTO mem = new MemberDTO();
			mem.setMemberNo(memberNo);
			
			ProjectDTO projectDTO = new ProjectDTO();
			projectDTO.setDepartmentDTO(dept);
			projectDTO.setIngDTO(ing);
			projectDTO.setProjectEndAt(projectEndAt);
			projectDTO.setProjectEndAt(projectEndAt);
			projectDTO.setProjectTitle(projectTitle);
			projectDTO.setMemberDTO(mem); // DTO안에 DTO를 쓰려면 안에 일부만 채우는게 아니라 꽉 채워야 하는건가?
			     
			     int addResult = projectMapper.addProjectM(projectDTO);
			     
			     return addResult;
			     
			   }
	
		@Override
		public int addProjectW(MultipartHttpServletRequest multipartHttpRequest) {
			String projectWorkTitle = multipartHttpRequest.getParameter("projectWorkTitle");
			String projectWorkDetail = multipartHttpRequest.getParameter("projectWorkDetail");
			String projectWorkModifiedAt = multipartHttpRequest.getParameter("projectWorkModifiedAt");
			String memberName = multipartHttpRequest.getParameter("memberName");
			int ingNo = multipartHttpRequest.getParameter("ingNo").isEmpty() ? 1 : Integer.parseInt(multipartHttpRequest.getParameter("ingNo"));
			int memberNo = multipartHttpRequest.getParameter("memberNo").isEmpty() ? 1 : Integer.parseInt(multipartHttpRequest.getParameter("memberNo"));
			int projectNo = multipartHttpRequest.getParameter("projectNo").isEmpty() ? 1 : Integer.parseInt(multipartHttpRequest.getParameter("projectNo"));
			
			String ingName = projectMapper.selectIng(ingNo).getIngState();
			
			IngDTO ing = new IngDTO();
			ing.setIngNo(ingNo);
			ing.setIngState(ingName);
			
			MemberDTO mem = new MemberDTO();
			mem.setMemberName(memberName);
			mem.setMemberNo(memberNo);
			
			ProjectDTO projectDTO = new ProjectDTO();
			projectDTO.setProjectNo(projectNo);
			
			ProjectWorkDTO projectWorkDTO = new ProjectWorkDTO();
			projectWorkDTO.setIngDTO(ing);
			projectWorkDTO.setMemberDTO(mem);
			projectWorkDTO.setProjectDTO(projectDTO);
			projectWorkDTO.setProjectWorkDetail(projectWorkDetail);
			projectWorkDTO.setProjectWorkModifiedAt(projectWorkModifiedAt);
			projectWorkDTO.setProjectWorkTitle(projectWorkTitle);
			
			int addResult = projectMapper.addProjectW(projectWorkDTO);
			return addResult;
			
		}
		
		@Override
		public int deleteProjectM(int projectNo) {
			
			int removeResult = projectMapper.deleteProjectM(projectNo);
			
			return removeResult;
		}
		
		@Override
		public int deleteProjectW(int projectWorkNo) {
			
			int removeResult = projectMapper.deleteProjectW(projectWorkNo);
			
			return removeResult;
		}
		
		
		@Override
		public int updateProjectM(HttpServletRequest request) {
			int projectNo = Integer.parseInt(request.getParameter("projectNo"));
			String memberName = request.getParameter("memberName");
			String projectTitle = request.getParameter("projectTitle");
			String projectModifiedAt = request.getParameter("projectModifiedAt");
			String projectStartAt = request.getParameter("projectStartAt");
			String projectEndAt = request.getParameter("projectEndAt");
			int deptNo = request.getParameter("deptNo").isEmpty() ? 1 : Integer.parseInt(request.getParameter("deptNo"));
			int memberNo = request.getParameter("memberNo").isEmpty() ? 1 : Integer.parseInt(request.getParameter("memberNo"));  
			int ingNo = request.getParameter("ingNo").isEmpty() ? 1 : Integer.parseInt(request.getParameter("ingNo"));
			
			String deptName = memberMapper.selectDept(deptNo).getDeptName();
			String ingName = projectMapper.selectIng(ingNo).getIngState();
			
			
			DepartmentDTO dept = new DepartmentDTO();
			dept.setDeptName(deptName);
			dept.setDeptNo(deptNo);
			
			IngDTO ing = new IngDTO();
			ing.setIngNo(ingNo);
			ing.setIngState(ingName);
			
			MemberDTO mem = new MemberDTO();
			mem.setMemberNo(memberNo);
			mem.setMemberName(memberName);
			
			ProjectDTO projectDTO = new ProjectDTO();
			projectDTO.setDepartmentDTO(dept);
			projectDTO.setIngDTO(ing);
			projectDTO.setMemberDTO(mem);
			projectDTO.setProjectEndAt(projectEndAt);
			projectDTO.setProjectModifiedAt(projectModifiedAt);
			projectDTO.setProjectNo(projectNo);
			projectDTO.setProjectStartAt(projectStartAt);
			projectDTO.setProjectTitle(projectTitle);
			
			int modifyResult = projectMapper.updateProjectM(projectDTO);
			return modifyResult;
		}
		
		@Override
		public int updateProjectW(MultipartHttpServletRequest multipartrequest) {
			
			int projectWorkNo = multipartrequest.getParameter("projectWorkNo").isEmpty() ? 1 : Integer.parseInt(multipartrequest.getParameter("projectWorkNo"));
			String memberName = multipartrequest.getParameter("memberName");
			String projectWorkDetail = multipartrequest.getParameter("projectWorkDetail");
			String projectWorkTitle = multipartrequest.getParameter("projectWorkTitle");
			String projectWorkModifiedAt = multipartrequest.getParameter("projectWorkModifiedAt");
			int ingNo = multipartrequest.getParameter("ingNo").isEmpty() ? 1 : Integer.parseInt(multipartrequest.getParameter("ingNo"));
			int deptNo = multipartrequest.getParameter("deptNo").isEmpty() ? 1 : Integer.parseInt(multipartrequest.getParameter("deptNo"));
			int memberNo = multipartrequest.getParameter("memberNo").isEmpty() ? 1 : Integer.parseInt(multipartrequest.getParameter("memberNo")); 
			
			String deptName = memberMapper.selectDept(deptNo).getDeptName();
			String ingName = projectMapper.selectIng(ingNo).getIngState();
			
			DepartmentDTO dept = new DepartmentDTO();
			dept.setDeptName(deptName);
			dept.setDeptNo(deptNo);
			
			IngDTO ing = new IngDTO();
			ing.setIngNo(ingNo);
			ing.setIngState(ingName);
			
			MemberDTO mem = new MemberDTO();
			mem.setMemberNo(memberNo);
			mem.setMemberName(memberName);
			
			ProjectWorkDTO projectWorkDTO = new ProjectWorkDTO();
			projectWorkDTO.setIngDTO(ing);
			projectWorkDTO.setMemberDTO(mem);
			projectWorkDTO.setProjectWorkDetail(projectWorkDetail);
			projectWorkDTO.setProjectWorkModifiedAt(projectWorkModifiedAt);
			projectWorkDTO.setProjectWorkNo(projectWorkNo);
			projectWorkDTO.setProjectWorkTitle(projectWorkTitle);
			
			int modifyResult = projectMapper.updateProjectW(projectWorkDTO); 
			
			return modifyResult;
		}
		
		@Override
		public void getProjectList(HttpServletRequest request, Model model) {
		    Optional<String> opt1 = Optional.ofNullable(request.getParameter("page"));
		    int page = Integer.parseInt(opt1.orElse("1"));
		    
		    int totalRecord = projectMapper.getProjectCount();
		    
		    int recordPerPage = 10;
		    
		    Optional<String> opt2 = Optional.ofNullable(request.getParameter("column"));
		    String column = opt2.orElse("P.PROJECT_NO");
		    
		    Optional<String> opt3 = Optional.ofNullable(request.getParameter("query"));
		    String query = opt3.orElse("");
		    
		    Map<String, Object> map = new HashMap<String, Object>();

		    map.put("column", column);
		    map.put("query", query);
		    map.put("recordPerPage", recordPerPage);    
		    pageUtil.setPageUtil(page, (column.isEmpty() && query.isEmpty()) ? totalRecord : projectMapper.getProjectSearchCount(map), recordPerPage);
		    map.put("begin", pageUtil.getBegin());      
			

		    List<ProjectDTO> projectList = projectMapper.getProjectMList(map);
		    model.addAttribute("projectList", projectList);
		    if(column.isEmpty() || query.isEmpty()) {
		      model.addAttribute("pagination", pageUtil.getPagination(request.getContextPath() + "/project/projectList.do"));
		    } else {
		      model.addAttribute("pagination", pageUtil.getPagination(request.getContextPath() + "/project/projectList.do?column=" + column + "&query=" + query));
		    }
		  }
		
		
		@Override
		public Map<String, Object> getProjectWorkList(HttpServletRequest request) {
			
		    Optional<String> opt1 = Optional.ofNullable(request.getParameter("page"));
		    int page = Integer.parseInt(opt1.orElse("1"));
		    int totalRecord = projectMapper.getProjectWCount();
		    
		    int recordPerPage = 5;
		    
		    Optional<String> opt2 = Optional.ofNullable(request.getParameter("column"));
		    String column = opt2.orElse("W.PROJECT_WORK_NO");
		    
		    Optional<String> opt3 = Optional.ofNullable(request.getParameter("query"));
		    String query = opt3.orElse("");
		    
		    Map<String, Object> map = new HashMap<String, Object>();
		    map.put("column", column);
		    map.put("query", query);
		    // PageUtil(Pagination에 필요한 모든 정보) 계산하기
		    map.put("recordPerPage", recordPerPage);    // end 대신 recordPerPage를 전달한다.
		    pageUtil.setPageUtil(page, (column.isEmpty() && query.isEmpty()) ? totalRecord : projectMapper.getProjectWSearchCount(map), recordPerPage);
		    map.put("begin", pageUtil.getBegin());      // begin은 0부터 시작한다. (PageUtil.java 참고)

		    List<ProjectWorkDTO> projectList = projectMapper.getProjectWList(map);
		    
		    Map<String, Object> result = new HashMap<String, Object>();
		    result.put("query", query);
		    result.put("memberList", projectList);
		    
		    result.put("pageUtil", pageUtil);
		    result.put("pagination", pageUtil.getPagination("/project/projectWorkList.do?column=" + column + "&query=" + query));
		    System.out.println(result.get("pageUtil"));
		    System.out.println(result);
		    System.out.println(pageUtil.getBeginPage());
		    System.out.println(pageUtil.getEndPage());
		    System.out.println(pageUtil.getTotalPage());
		    return result;
		  }
		
		@Override
		public void projectWDetail(int projectWorkNo, Model model) {
			ProjectWorkDTO projectWorkDTO = new ProjectWorkDTO();
			projectWorkDTO = projectMapper.getProjectWByNo(projectWorkNo);
			model.addAttribute("work", projectWorkDTO);
			model.addAttribute("deptList", projectMapper.getDeptList());
		}
	}
		






