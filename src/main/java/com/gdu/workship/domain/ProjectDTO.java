package com.gdu.workship.domain;

import java.sql.Date;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class ProjectDTO {
	private int projectNo;
	private MemberDTO memberDTO;
	private int projectState;
	private Date projectStartAt; 
	private Date projectEndAt; 
	private Date projectModifiedAt;
	private String projectTitle;
	private String projectDetail;
}
