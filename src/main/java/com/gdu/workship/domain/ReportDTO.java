package com.gdu.workship.domain;

import java.util.Date;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class ReportDTO {
	private int reportNo;
	private BoardDTO boardDTO;
	private MemberDTO memberDTO;
	private String reportContent;
	private int reportState;
	private Date reportDate;
	private Date reportDoneDate;
}