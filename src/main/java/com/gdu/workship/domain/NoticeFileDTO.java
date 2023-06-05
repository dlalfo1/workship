package com.gdu.workship.domain;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class NoticeFileDTO {
	private int noticeFileNo;
	private NoticeDTO noticeDTO;
	private String noticeFilePath;
	private String noticeFileOriginName;
	private String noticeFileSystemName;
}