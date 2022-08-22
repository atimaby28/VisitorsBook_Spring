package com.visitor.visitorsbook.model;

public class VisitorDto {

	private String visitorName;
	private String visitorId;
	private String visitorPwd;
	private String email;
	private String joinDate;

	

	public String getVisitorName() {
		return visitorName;
	}

	public void setVisitorName(String visitorName) {
		this.visitorName = visitorName;
	}

	public String getVisitorId() {
		return visitorId;
	}

	public void setVisitorId(String visitorId) {
		this.visitorId = visitorId;
	}

	public String getVisitorPwd() {
		return visitorPwd;
	}

	public void setVisitorPwd(String visitorPwd) {
		this.visitorPwd = visitorPwd;
	}

	public String getEmail() {
		return email;
	}

	public void setEmail(String email) {
		this.email = email;
	}

	public String getJoinDate() {
		return joinDate;
	}

	public void setJoinDate(String joinDate) {
		this.joinDate = joinDate;
	}

	@Override
	public String toString() {
		return "VisitorDto [visitorName=" + visitorName + ", visitorId=" + visitorId + ", visitorPwd=" + visitorPwd + ", email=" + email
				+ ", joinDate=" + joinDate + "]";
	}

}
