package com.gdu.workship.domain;

import java.sql.Time;
import java.sql.Timestamp;
import java.util.Date;

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
	private String attendance;
	private int worktime;
}