package com.gdu.workship.domain;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class ProjectDTO {
	private int projectNo;
	private MemberDTO memberDTO;
	private DepartmentDTO departmentDTO;
	private String projectStartAt; 
	private String projectEndAt; 
	private String projectModifiedAt;
	private String projectTitle;
	private IngDTO ingDTO;
}
