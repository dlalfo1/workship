package com.gdu.workship.domain;

import java.util.Date;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class BoardDTO {
	
	private int boardNo;
	private MemberDTO MemberDTO;
	private String boardTitle;
	private String boardContent;
	private Date boardCreatedAt;
	private Date boardModifiedAt;
	private int boardHit;
	private int boardState;
	
	private int prevNo;
	private int nextNo;
}
