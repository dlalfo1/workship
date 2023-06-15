package com.gdu.workship.service;

import java.util.Date;
import java.util.List;

import javax.servlet.http.HttpServletRequest;

import org.springframework.stereotype.Service;
import org.springframework.ui.Model;

import com.gdu.workship.domain.AttendanceDTO;
import com.gdu.workship.mapper.AttendanceMapper;

import lombok.RequiredArgsConstructor;

@RequiredArgsConstructor
@Service
public class AttendManageServiceImpl implements AttendManageService {

	private final AttendanceMapper attendManageMapper;
	
	@Override
	public void updateAllScehduler() {
		List<Integer> memberNoList = attendManageMapper.getAllMemberNo();
		for(int memberNo : memberNoList) {
			AttendanceDTO attendanceDTO = attendManageMapper.getAttendanceYesterday(memberNo);
			if(attendanceDTO == null) attendManageMapper.addAbsent(memberNo);
			else {
				Date aEndTime = attendanceDTO.getAendtime();
				if(aEndTime == null) attendManageMapper.updateError(memberNo);
			}
		}
	}
	
	@Override
	public void getAttendManagePage(HttpServletRequest request, Model model) {
		
	}
}
