package com.gdu.workship.domain;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class ProjectWorkDTO {

	private int projectWorkNo;
	private ProjectDTO projectDTO;
	private DepartmentDTO departmentDTO;
	private MemberDTO memberDTO;
	private String projectWorkTitle;
	private String projectWorkDetail;
	private IngDTO ingDTO;
	private String projectWorkModifiedAt;
}
