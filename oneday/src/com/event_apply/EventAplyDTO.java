package com.event_apply;

public class EventAplyDTO {
	private int eNum;
	private String userId, userName;
	private int applyEnabled;
	
	public int geteNum() {
		return eNum;
	}
	public void seteNum(int eNum) {
		this.eNum = eNum;
	}
	public String getUserId() {
		return userId;
	}
	public void setUserId(String userId) {
		this.userId = userId;
	}
	public String getUserName() {
		return userName;
	}
	public void setUserName(String userName) {
		this.userName = userName;
	}
	public int getApplyEnabled() {
		return applyEnabled;
	}
	public void setApplyEnabled(int applyEnabled) {
		this.applyEnabled = applyEnabled;
	}
	

	
	
}
