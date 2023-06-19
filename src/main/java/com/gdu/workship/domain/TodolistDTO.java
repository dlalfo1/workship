package com.gdu.workship.domain;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class TodolistDTO {
	
	private int todolistNo;
	private int memberNo;
	private String todoTitle;
	private String todoMemo;
	private int todoState; // 1 - 할 일, 2 - 진행중, 3 - 완료

}