package com.gdu.workship.domain;

import java.util.Date;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class MailDTO {
	private int mailNo;
	private MemberDTO memberDTO;
	private String mailTitle;
	private String mailContent;
	private String mailHasFile;
	private Date mailDate;
	private String mailCategory;
}