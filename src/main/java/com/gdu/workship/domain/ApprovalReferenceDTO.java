package com.gdu.workship.domain;

import java.util.Date;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import com.gdu.workship.domain.ApprovalDTO;
import com.gdu.workship.domain.MemberDTO;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class ApprovalReferenceDTO {
	
	private int referenceNo;
  private MemberDTO memberDTO;
  private ApprovalDTO approvalDTO;

}
