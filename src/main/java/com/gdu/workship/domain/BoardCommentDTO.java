package com.gdu.workship.domain;

import java.util.Date;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class BoardCommentDTO {
	
	private int commentNo;
	private BoardDTO boardDTO;
	private String commentContent;
	private int commentState;
	private int commentDepth;
	private int commentGroupNo;
	private Date commentCreatedAt;
	private int commentGroupOrder;
	private int boardNo;
	private MemberDTO memberDTO;
}
