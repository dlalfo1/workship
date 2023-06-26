package com.gdu.workship.service;

import java.time.LocalDate;
import java.time.temporal.ChronoUnit;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Optional;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpSession;

import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.ui.Model;

import com.gdu.workship.domain.MemberDTO;
import com.gdu.workship.domain.VacationApprovalDTO;
import com.gdu.workship.domain.VacationDTO;
import com.gdu.workship.mapper.VacationMapper;
import com.gdu.workship.util.PageUtil2;

import lombok.RequiredArgsConstructor;

@RequiredArgsConstructor
@Service
public class VacationServiceImpl implements VacationService {

	private final VacationMapper vacationMapper;
	private final PageUtil2 pageUtil;
	
	@Override
	public void getPersonalVacationInfo(HttpSession session, Model model) {
		int memberNo = ((MemberDTO)(session.getAttribute("loginMember"))).getMemberNo();
		MemberDTO memberDTO = vacationMapper.getPersonalVacationInfo(memberNo);
		int totalDayoff = memberDTO.getTotalDayoff();
		int dayoffCount = memberDTO.getDayoffCount();
		List<VacationDTO> vacationList = vacationMapper.getPersonalVacationList(memberNo);
		model.addAttribute("totalDayoff", totalDayoff);
		model.addAttribute("dayoffCount", dayoffCount);
		model.addAttribute("vacationList", vacationList);
	}

	@Override
	public void getApprovalList(HttpServletRequest request, Model model) {
		Map<String, Object> parameter = new HashMap<>();
		LocalDate now = LocalDate.now();
		String thismonth = LocalDate.of(now.getYear(), now.getMonthValue(), 1).toString();
		String today = LocalDate.of(now.getYear(), now.getMonthValue(), now.getDayOfMonth() + 1).toString();
		String startDate = request.getParameter("startDate");
		String endDate = request.getParameter("endDate");
		if(startDate == null || startDate.equals("")) startDate = thismonth;
		if(endDate == null || endDate.equals("")) endDate = today;
		parameter.put("startDate", startDate);
		parameter.put("endDate", endDate);
		String vacationCategoryPath = "";
		try {
			String[] vacationCategoryArr = request.getParameterValues("vacationCategory");
			int length = Optional.ofNullable(vacationCategoryArr.length).orElse(1);
			String vacationCategory = "";
			for(int i = 0; i < length - 1; i++) {
				vacationCategoryPath += "&vacationCategory=" + vacationCategoryArr[i];
				vacationCategory += "'" + vacationCategoryArr[i] + "', ";
			}
			vacationCategoryPath += "&vacationCategory=" + vacationCategoryArr[length - 1];
			vacationCategory += "'" + vacationCategoryArr[length - 1] + "'";
			parameter.put("vacationCategory", vacationCategory);
		} catch (Exception e) {
		}
		String vacationState = Optional.ofNullable(request.getParameter("vacationState")).orElse("1");
		String queryNum = Optional.ofNullable(request.getParameter("queryNum")).orElse("");
		String queryName = Optional.ofNullable(request.getParameter("queryName")).orElse("");
		parameter.put("vacationState", vacationState);
		parameter.put("queryNum", queryNum);
		parameter.put("queryName", queryName);
		int totalRecord = vacationMapper.getApprovalCount(parameter);
		int recordPerPage = 10;
		Optional<String> pageStr = Optional.ofNullable(request.getParameter("page"));
		int page = Integer.parseInt(pageStr.orElse("1"));
		pageUtil.setPageUtil(page, totalRecord, recordPerPage);
		parameter.put("begin", pageUtil.getBegin());
		parameter.put("recordPerPage", recordPerPage);
		List<VacationApprovalDTO> approvalList = vacationMapper.approvalSearchList(parameter);
		model.addAttribute("approvalList", approvalList);
		String path = "/vacation/approvalSearch.do?startDate=" + startDate + "&endDate=" + endDate + vacationCategoryPath + "&vacationState=" + vacationState + "&queryNum=" + queryNum + "&queryName=" + queryName;
		model.addAttribute("approvalPagination", pageUtil.getPagination(path));
	}
	
	@Transactional
	@Override
	public Map<String, Object> updateApproval(HttpServletRequest request) {
		int approvalNo = Integer.parseInt(request.getParameter("approvalNo"));
		int updateResult = vacationMapper.updateApproval(approvalNo);
		VacationApprovalDTO approvalDTO = vacationMapper.selectApprovalByApprovalNo(approvalNo);
		String vacationStartDate = approvalDTO.getVacationStartDate().toString();
		String vacationEndDate = approvalDTO.getVacationEndDate().toString();
		int memberNo = approvalDTO.getMemberDTO().getMemberNo();
		Map<String, Object> parameter = new HashMap<>();
		parameter.put("memberNo", memberNo);
		parameter.put("approvalNo", approvalNo);
		parameter.put("vacationStartDate", vacationStartDate);
		parameter.put("vacationEndDate", vacationEndDate);
		int insertResult = vacationMapper.insertVacation(parameter);
		int vacationDays = vacationMapper.getVacationDay(approvalNo);
		parameter.put("vacationDays", vacationDays);
		vacationMapper.updateDayoffCount(parameter);
		Map<String, Object> map = new HashMap<>();
		if(updateResult == 1) map.put("updateResult", "success");
		else map.put("updateResult", "fail");
		if(insertResult == 1) map.put("insertResult", "success");
		else map.put("insertResult", "fail");
		return map;
	}
	
	@Override
	public void getVacationList(HttpServletRequest request, Model model) {
		Map<String, Object> parameter = new HashMap<>();
		LocalDate now = LocalDate.now();
		String thismonth = LocalDate.of(now.getYear(), now.getMonthValue(), 1).toString();
		String today = LocalDate.of(now.getYear(), now.getMonthValue(), now.getDayOfMonth() + 1).toString();
		String startDate = request.getParameter("startDate");
		String endDate = request.getParameter("endDate");
		if(startDate == null || startDate.equals("")) startDate = thismonth;
		if(endDate == null || endDate.equals("")) endDate = today;
		parameter.put("startDate", startDate);
		parameter.put("endDate", endDate);
		String vacationCategoryPath = "";
		try {
			String[] vacationCategoryArr = request.getParameterValues("vacationCategory");
			int length = Optional.ofNullable(vacationCategoryArr.length).orElse(1);
			String vacationCategory = "";
			for(int i = 0; i < length - 1; i++) {
				vacationCategoryPath += "&vacationCategory=" + vacationCategoryArr[i];
				vacationCategory += "'" + vacationCategoryArr[i] + "', ";
			}
			vacationCategoryPath += "&vacationCategory=" + vacationCategoryArr[length - 1];
			vacationCategory += "'" + vacationCategoryArr[length - 1] + "'";
			parameter.put("vacationCategory", vacationCategory);
		} catch (Exception e) {
		}
		String queryNum = Optional.ofNullable(request.getParameter("queryNum")).orElse("");
		String queryName = Optional.ofNullable(request.getParameter("queryName")).orElse("");
		parameter.put("queryNum", queryNum);
		parameter.put("queryName", queryName);
		int totalRecord = vacationMapper.getVacationCount(parameter);
		int recordPerPage = 10;
		Optional<String> pageStr = Optional.ofNullable(request.getParameter("page"));
		int page = Integer.parseInt(pageStr.orElse("1"));
		pageUtil.setPageUtil(page, totalRecord, recordPerPage);
		parameter.put("begin", pageUtil.getBegin());
		parameter.put("recordPerPage", recordPerPage);
		List<VacationDTO> vacationList = vacationMapper.vacationSearchList(parameter);
		model.addAttribute("vacationList", vacationList);
		String path = "/vacation/vacationSearch.do?startDate=" + startDate + "&endDate=" + endDate + vacationCategoryPath + "&queryNum=" + queryNum + "&queryName=" + queryName;
		model.addAttribute("vacationPagination", pageUtil.getPagination(path));
		
	}

	@Transactional
	@Override
	public Map<String, Object> modifyVacation(HttpServletRequest request) {
		int approvalNo = Integer.parseInt(request.getParameter("approvalNo"));
		String vacationCategory = request.getParameter("vacationCategory");
		String vacationStartDate = request.getParameter("vacationStartDate");
		String vacationEndDate = request.getParameter("vacationEndDate");
		LocalDate startDate = LocalDate.of(Integer.parseInt(vacationStartDate.substring(0,4)), Integer.parseInt(vacationStartDate.substring(5,7)), Integer.parseInt(vacationStartDate.substring(8,10)));
		LocalDate endDate = LocalDate.of(Integer.parseInt(vacationEndDate.substring(0,4)), Integer.parseInt(vacationEndDate.substring(5,7)), Integer.parseInt(vacationEndDate.substring(8,10)));
		Map<String, Object> parameter = new HashMap<>();
		parameter.put("approvalNo", approvalNo);
		parameter.put("vacationCategory", vacationCategory);
		parameter.put("vacationStartDate", startDate);
		parameter.put("vacationEndDate", endDate);
		int updateApprovalResult = vacationMapper.modifyVacationApproval(parameter);
		Map<String, Object> map = new HashMap<>();
		map.put("updateApprovalResult", updateApprovalResult);
		int vacationNo = Integer.parseInt(request.getParameter("vacationNo"));
		int recentVacationDay = vacationMapper.getVacationDay(approvalNo);
		int modifiedvacationDay = (int)ChronoUnit.DAYS.between(startDate, endDate) + 1;
		int vacationDayGap = modifiedvacationDay - recentVacationDay;
		VacationDTO vacationDTO = new VacationDTO();
		vacationDTO.setVacationNo(vacationNo);
		vacationDTO.setVacationDay(modifiedvacationDay);
		int updateVacationResult = vacationMapper.modifyVacationDay(vacationDTO);
		int memberNo = vacationMapper.selectMemberNoVacationDayByVacationNo(vacationNo).getMemberDTO().getMemberNo();
		Map<String, Object> parameter2 = new HashMap<>();
		parameter2.put("memberNo", memberNo);
		parameter2.put("vacationDays", vacationDayGap);
		vacationMapper.updateDayoffCount(parameter2);
		map.put("updateVacationResult", updateVacationResult);
		map.put("vStartDate", vacationStartDate);
		map.put("vEndDate", vacationEndDate);
		map.put("vCategory", vacationCategory);
		map.put("vacationDay", modifiedvacationDay);
		return map;
	}
	
	@Transactional
	@Override
	public Map<String, Object> removeVacation(int vacationNo) {
		VacationDTO vacationDTO = vacationMapper.selectMemberNoVacationDayByVacationNo(vacationNo);
		int memberNo = vacationDTO.getMemberDTO().getMemberNo();
		int vacationDay = vacationDTO.getVacationDay() * (-1);
		int deleteResult = vacationMapper.deleteVacationByVacationNo(vacationNo);
		Map<String, Object> parameter = new HashMap<>();
		parameter.put("memberNo", memberNo);
		parameter.put("vacationDays", vacationDay);
		int updateDayoffResult = vacationMapper.updateDayoffCount(parameter);
		Map<String, Object> map = new HashMap<>();
		map.put("deleteResult", deleteResult);
		map.put("updateDayoffResult", updateDayoffResult);
		return map;
	}
	
}
