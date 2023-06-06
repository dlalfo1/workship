package com.gdu.workship.domain;

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
	private Timestamp astarttime;
	private Timestamp aendtime;
	private int worktime;
}