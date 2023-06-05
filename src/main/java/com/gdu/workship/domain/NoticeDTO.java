package com.gdu.workship.domain;

import java.util.Date;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class NoticeDTO {
	private int noticeNo;
	private MemberDTO memberDTO;
	private String noticeTitle;
	private String noticeContent;
	private Date noticeCreatedAt;
	private Date noticeModifiedAt;
	private int noticeHit;
	private int noticeState;
}