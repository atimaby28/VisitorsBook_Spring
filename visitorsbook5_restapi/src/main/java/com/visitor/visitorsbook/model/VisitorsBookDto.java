package com.visitor.visitorsbook.model;

import java.util.List;

public class VisitorsBookDto {
	private int articleNo;
	private String visitorId;
	private String visitorName;
	private String subject;
	private String content;
	private String regTime;
	private List<FileInfoDto> fileInfos;

	public int getArticleNo() {
		return articleNo;
	}

	public void setArticleNo(int articleNo) {
		this.articleNo = articleNo;
	}

	public String getVisitorId() {
		return visitorId;
	}
	
	public void setVisitorId(String visitorId) {
		this.visitorId = visitorId;
	}
	
	public String getVisitorName() {
		return visitorName;
	}
	
	public void setVisitorName(String visitorName) {
		this.visitorName = visitorName;
	}

	public String getSubject() {
		return subject;
	}

	public void setSubject(String subject) {
		this.subject = subject;
	}

	public String getContent() {
		return content;
	}

	public void setContent(String content) {
		this.content = content;
	}

	public String getRegTime() {
		return regTime;
	}

	public void setRegTime(String regTime) {
		this.regTime = regTime;
	}

	public List<FileInfoDto> getFileInfos() {
		return fileInfos;
	}

	public void setFileInfos(List<FileInfoDto> fileInfos) {
		this.fileInfos = fileInfos;
	}

}
