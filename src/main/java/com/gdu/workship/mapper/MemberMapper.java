package com.gdu.workship.mapper;

import java.util.List;
import java.util.Map;

import org.apache.ibatis.annotations.Mapper;

import com.gdu.workship.domain.DepartmentDTO;
import com.gdu.workship.domain.JobDTO;
import com.gdu.workship.domain.MemberDTO;

@Mapper
public interface MemberMapper {
  public List<DepartmentDTO> getDeptList();
  public int getMemberCount();
  public List<MemberDTO> getMemberList(Map<String, Object> map);
  public int getMemberSearchCount(Map<String, Object> map);
  public DepartmentDTO selectDept(int deptNo);
  public JobDTO selectJob(int jobNo);
  public int createMember(MemberDTO member);
  

}
