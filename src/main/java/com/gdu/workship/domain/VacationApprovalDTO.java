package com.gdu.workship.domain;


import java.sql.Date;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class VacationApprovalDTO {
   
  private int approvalNo;
  private MemberDTO memberDTO;
  private int docName;
  private String docTitle;
  private Date createdAt;
  private String vacationCategory;
  private Date vacationStartDate;
  private Date vacationEndDate;
  private int vacationState;
  
}