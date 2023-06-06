package com.gdu.workship.domain;

import java.util.Date;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class ApprovalLineDTO {
	
	private int approvalLineNo;
	private MemberDTO memberDTO;
	private ApprovalDTO approvalDTO;
  private int approvalOrder;
  private int memberApprovalStatus;
  private Date approvalDate;
  private String docComment;
 
}
