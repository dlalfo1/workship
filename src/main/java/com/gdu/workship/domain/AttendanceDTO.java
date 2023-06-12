package com.gdu.workship.domain;

import java.sql.Date;
import java.sql.Time;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class AttendanceDTO {
	private int memberNo;
	private Date date;
	private Time astarttime;
	private Time aendtime;
	private Time worktime;
	private String attendance;
}