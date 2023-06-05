package com.gdu.workship.domain;

import java.sql.Date;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class ProjectWorkDTO {

	private int projectWorkNo;
	private ProjectDTO projectDTO;
	private MemberDTO memberDTO;
	private String projectWorkTitle;
	private String projectWorkDetail;
	private int projectWorkState;
	private Date projectWorkModifiedAt;
}
