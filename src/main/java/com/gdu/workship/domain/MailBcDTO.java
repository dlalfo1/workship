package com.gdu.workship.domain;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class MailBcDTO {
	private int mailBcNo;
	private MailDTO mailDTO;
	private String mailBcTo;
	private String mailCategory;
	private String mailStatus;
	private String mailStar;	
}
