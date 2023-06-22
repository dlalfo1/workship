package com.gdu.workship.mapper;

import java.util.List;
import java.util.Map;

import org.apache.ibatis.annotations.Mapper;

import com.gdu.workship.domain.DepartmentDTO;
import com.gdu.workship.domain.JobDTO;
import com.gdu.workship.domain.MemberDTO;
import com.gdu.workship.domain.RetiredMemberDTO;

@Mapper
public interface MemberMapper {
  public List<DepartmentDTO> getDeptList();
  public int getMemberCount();
  public List<MemberDTO> getMemberList(Map<String, Object> map);
  public int getRetiredMemberCount();
  public List<RetiredMemberDTO> getRetiredMemberList();
  public int getMemberSearchCount(Map<String, Object> map);
  public DepartmentDTO selectDept(int deptNo);
  public JobDTO selectJob(int jobNo);
  public MemberDTO selectMemberByEmailId(String emailId);
  public int createMember(MemberDTO member);
  public MemberDTO getMemberByNo(int memberNo);
  public int modifyMember(MemberDTO member);
  public int removeMember(int memberNo);
}
