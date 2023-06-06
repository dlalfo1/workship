package com.gdu.workship.domain;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class ApprovalReferenceDTO {
	
	private int referenceNo;
  private MemberDTO memberDTO;
  private ApprovalDTO approvalDTO;

}
