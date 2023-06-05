package com.gdu.workship.util;

import org.springframework.stereotype.Component;

import lombok.Getter;

@Component
@Getter
public class PageUtil {

	private int page;
	private int totalRecord;
	private int recordPerPage;
	private int begin;
	
	private int pagePerBlock = 5;
	private int totalPage;
	private int beginPage;
	private int endPage;
	
	public void setPageUtil(int page, int totalRecord, int recordPerPage) {
		
		this.page = page;
		this.totalRecord = totalRecord;
		this.recordPerPage = recordPerPage;
		
		begin = (page - 1) * recordPerPage;
		
		totalPage = totalRecord / recordPerPage;
		if(totalRecord % recordPerPage != 0) {
			totalPage ++;
		}
		
		beginPage = ((page - 1) / pagePerBlock) * pagePerBlock + 1;
		endPage = beginPage + pagePerBlock - 1;
		if(endPage > totalPage) endPage = totalPage;
		
	}
	
	public String getPagination(String path) {
		if(path.contains("?")) path += "&";
		else path += "?";
		
		StringBuilder sb = new StringBuilder();
		sb.append("<div class=\"pagination\">");
		
		if(beginPage == 1) {
			sb.append("<span class=\"hidden\">◀</span>");
		} else {
			sb.append("<a class=\"link\" href=\"" + path + "page=" + (beginPage - 1) + "\">◀</a>");
		}
		
		for(int p = beginPage; p <= endPage; p++) {
			if(p == page) {
				sb.append("<span class=\"strong\">" + p + "</span>");
			} else {
				sb.append("<a class=\"link\" href=\"" + path + "page=" + p + "\">" + p + "</a>");
			}
		}
		
		if(endPage == totalPage) {
			sb.append("<span class=\"hidden\">▶</span>");
		} else {
			sb.append("<a class=\"link\" href=\"" + path + "page=" + (endPage + 1) + "\">▶</a>");
		}
		
		sb.append("</div>");
		
		return sb.toString();
		
	}
	
}
