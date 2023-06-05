package com.gdu.workship.domain;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class MailFileDTO {
	private int mailFileNo;
	private MailDTO mailDTO;
	private String mailFilePath;
	private String mailFileOriginName;
	private String mailFileSystemName;
}
