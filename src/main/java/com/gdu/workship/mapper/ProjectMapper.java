package com.gdu.workship.mapper;

import java.util.List;
import java.util.Map;

import org.apache.ibatis.annotations.Mapper;

import com.gdu.workship.domain.DepartmentDTO;
import com.gdu.workship.domain.IngDTO;
import com.gdu.workship.domain.JobDTO;
import com.gdu.workship.domain.ProjectDTO;
import com.gdu.workship.domain.ProjectWorkDTO;

@Mapper
public interface ProjectMapper {
	
	// 프로젝트 추가
	public int addProjectM(ProjectDTO projectDTO);
	public IngDTO selectIng(int ingNo);
	public int deleteProjectM(int projectNo);
	public int updateProjectM(ProjectDTO projectDTO);
	public int addProjectW(ProjectWorkDTO projectWorkDTO);
	public int deleteProjectW(int projectWorkNo);
	public int updateProjectW(ProjectWorkDTO projectWorkDTO);
	public JobDTO getjob(int jobNo);
	public List<ProjectDTO> getProjectMList(Map<String, Object> map);
	public List<ProjectWorkDTO> getProjectWList(Map<String, Object> map);
	public int getProjectCount();
	public int getProjectWCount();
	public int getProjectSearchCount(Map<String, Object> map);
	public int getProjectWSearchCount(Map<String, Object> map);
	public ProjectWorkDTO getProjectWByNo(int projectWorkNo);
	public List<DepartmentDTO> getDeptList(); 
	public String getProjectName(int projectNo);
	public ProjectWorkDTO getProjectWork(int projectWorkNo);
	public ProjectDTO getProject(int projectNo);
}
