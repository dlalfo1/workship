package com.gdu.workship.domain;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class ApprovalFileDTO {
	
	private int approvalFileNo;
  private ApprovalDTO approvalDTO;
  private String approvalFileOriginName;
  private String approvalFileSystemName;
}
