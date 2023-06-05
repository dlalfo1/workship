package com.gdu.workship.domain;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class MailCcDTO {
	private int mailCcNo;
	private MailDTO mailDTO;
	private String mailCcTo;
	private String mailCategory;
	private String mailStatus;
	private String mailStar;	
}
