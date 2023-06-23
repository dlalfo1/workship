package com.gdu.workship.domain;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class VacationDTO {

	private int vacationNo;
	private MemberDTO memberDTO;
	private VacationApprovalDTO vacationApprovalDTO;
	private int vacationDay;
	
}