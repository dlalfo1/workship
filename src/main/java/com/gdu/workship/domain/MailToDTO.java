package com.gdu.workship.domain;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class MailToDTO {
	private int mailToNo;
	private MailDTO mailDTO;
	private String mailTo;
	private String mailToRole;
	private String mailToCategory;
	private String mailToStatus;
	private String mailToStar;	
}
