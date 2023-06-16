package com.gdu.workship.mapper;

import java.util.List;

import org.apache.ibatis.annotations.Mapper;

import com.gdu.workship.domain.DepartmentDTO;

@Mapper
public interface DepartmentMapper {
  public List<DepartmentDTO> getDeptList();
  public List<DepartmentDTO> getDeptHeadList();
  public DepartmentDTO checkDept(String deptName);
  public int addDept(DepartmentDTO departmentDTO);
  public int removeDept(int deptNo);
}
