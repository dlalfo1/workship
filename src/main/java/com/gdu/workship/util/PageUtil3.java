package com.gdu.workship.util;

import org.springframework.stereotype.Component;

@Component
public class PageUtil3 {

  private int page;
  private int totalRecord;
  private int recordPerPage;
  private int begin;
  private int end;
  
  private int pagePerBlock = 5;
  private int totalPage;
  private int beginPage;
  private int endPage;
  
  public void setPageUtil(int page, int totalRecord, int recordPerPage) {
    
    this.page = page;
    this.totalRecord = totalRecord;
    this.recordPerPage = recordPerPage;

    begin = (page - 1) * recordPerPage + 1;
    end = begin + recordPerPage - 1;
    if(end > totalRecord) end = totalRecord;

    totalPage = totalRecord / recordPerPage;
    if(totalRecord % recordPerPage != 0) totalPage++;

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
      sb.append("<li class=\"page-item disabled\"><span class=\"page-link text-secondary\">◀</span></li>");
    } else {
      sb.append("<li class=\"page-item\"><a class=\"page-link\" href=\"" + path + "page=" + (beginPage - 1) + "\">◀</a></li>");
    }
    
    for(int p = beginPage; p <= endPage; p++) {
      if(p == page) {
        sb.append("<li class=\"page-item active\"><a class=\"page-link\">" + p + "</a></li>");
      } else {
        sb.append("<li class=\"page-item\"><a class=\"page-link\" href=\"" + path + "page=" + p + "\">" + p + "</a></li>");
      }
    }
    
    if(endPage == totalPage) {
      sb.append("<li class=\"page-link text-secondary\">▶</span>");
    } else {
      sb.append("<a class=\"page-link text-secondary\" href=\"" + path + "page=" + (endPage + 1) + "\">▶</a>");
    } 
    
    sb.append("</ul></nav>");
    
    return sb.toString();
    
  }


  public int getPage() {
    return page;
  }

  public void setPage(int page) {
    this.page = page;
  }

  public int getTotalRecord() {
    return totalRecord;
  }

  public void setTotalRecord(int totalRecord) {
    this.totalRecord = totalRecord;
  }

  public int getRecordPerPage() {
    return recordPerPage;
  }

  public void setRecordPerPage(int recordPerPage) {
    this.recordPerPage = recordPerPage;
  }

  public int getBegin() {
    return begin;
  }

  public void setBegin(int begin) {
    this.begin = begin;
  }

  public int getPagePerBlock() {
    return pagePerBlock;
  }

  public void setPagePerBlock(int pagePerBlock) {
    this.pagePerBlock = pagePerBlock;
  }

  public int getTotalPage() {
    return totalPage;
  }

  public void setTotalPage(int totalPage) {
    this.totalPage = totalPage;
  }

  public int getBeginPage() {
    return beginPage;
  }

  public void setBeginPage(int beginPage) {
    this.beginPage = beginPage;
  }

  public int getEndPage() {
    return endPage;
  }

  public void setEndPage(int endPage) {
    this.endPage = endPage;
  }

}
