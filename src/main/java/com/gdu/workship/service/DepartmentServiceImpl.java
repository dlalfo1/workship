package com.gdu.workship.service;

import java.util.List;

import javax.servlet.http.HttpServletRequest;

import org.springframework.stereotype.Service;
import org.springframework.ui.Model;

import com.gdu.workship.domain.DepartmentDTO;
import com.gdu.workship.mapper.DepartmentMapper;

import lombok.RequiredArgsConstructor;

@RequiredArgsConstructor
@Service
public class DepartmentServiceImpl implements DepartmentService {

  private final DepartmentMapper departmentMapper;
  
  @Override
  public void loadDepartmentList(HttpServletRequest request, Model model) {
    List<DepartmentDTO> deptList = departmentMapper.getDeptList();
    List<DepartmentDTO> deptHeadList = departmentMapper.getDeptHeadList();
    for(int i = 0; i < deptList.size(); i++) {
      DepartmentDTO dept = deptList.get(i);
      if(deptHeadList.size() > i) {
        dept.setDeptHead(deptHeadList.get(i).getDeptHead());
      }
      deptList.set(i, dept);
    }
    
    model.addAttribute("deptList", deptList);
    System.out.println(deptList);
  }

  @Override
  public int addDept(HttpServletRequest request) {
    DepartmentDTO departmentDTO = new DepartmentDTO();
    String deptName = request.getParameter("deptName");

    departmentDTO.setDeptName(deptName);
    
    int addResult = 0;
    if(departmentMapper.checkDept(deptName) == null) {
      addResult = departmentMapper.addDept(departmentDTO);
      return addResult;
    } else {
      return addResult;
    }
  }
  
  @Override
  public int removeDept(HttpServletRequest request) {

    int deptNo = Integer.parseInt(request.getParameter("deptNo"));
    int removeResult = departmentMapper.removeDept(deptNo);
    return removeResult;
  }
  
}
