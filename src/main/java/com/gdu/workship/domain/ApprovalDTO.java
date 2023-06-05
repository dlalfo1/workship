package com.gdu.workship.domain;

import java.util.Date;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class ApprovalDTO {
	
	private int approvalNo;
	private MemberDTO memberDTO;
	private int docName;
	private String docTitle;
	private Date createdAt;
	private String docContent;
	private int docStatus;
	private int approvalStatus;
	private int approvalCount;
	private int approvalSequence;
	private Date vacatioinStartDate;
	private Date vacationEndDate;
	private Date payDate;
	private Date resignationDate;
	
}
