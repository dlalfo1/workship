package com.gdu.workship.domain;

import java.util.Date;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class MemberDTO {
	private int memberNo;
	private JobDTO jobDTO;
	private DepartmentDTO departmentDTO;
	private String memberName;
	private String emailId;
	private String tel;
	private String birthday;
	private Date joinedAt;
	private Date modifiedAt;
	private int status;
	private int dayoffCount;
	private String pw;
	private String profileFilePath;
	private String profileFileName;
}