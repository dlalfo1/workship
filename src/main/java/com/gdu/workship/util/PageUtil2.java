package com.gdu.workship.util;

import org.springframework.stereotype.Component;

import lombok.Getter;

@Component
@Getter
public class PageUtil2 {

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
		sb.append("<nav><ul class=\"pagination pagination-sm justify-content-center\">");
		if(beginPage == 1) {
			sb.append("<li class=\"page-item disabled\"><span class=\"page-link text-secondary\">이전</span></li>");
		} else {
			sb.append("<li class=\"page-item\"><a class=\"page-link\" href=\"" + path + "page=" + (beginPage - 1) + "\">이전</a></li>");
		}
		
		for(int p = beginPage; p <= endPage; p++) {
			if(p == page) {
				sb.append("<li class=\"page-item active\"><a class=\"page-link\">" + p + "</a></li>");
			} else {
				sb.append("<li class=\"page-item\"><a class=\"page-link\" href=\"" + path + "page=" + p + "\">" + p + "</a></li>");
			}
		}
		
		if(endPage == totalPage) {
			sb.append("<li class=\"page-item disabled\"><span class=\"page-link text-secondary\">다음</span></li>");
		} else {
			sb.append("<li class=\"page-item\"><a class=\"page-link\" href=\"" + path + "page=" + (endPage + 1) + "\">다음</a></li>");
		}
		sb.append("</ul></nav>");

		return sb.toString();
		
	}
	
}
