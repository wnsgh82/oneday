package com.event;

public class EventDTO {
	private int eNum;
	private String userId, userName;
	private String eName;
	private String eSubject;
	private String eContent;
	private String eDate;
	private String eFIN;
	private int eHitCount;
	private int userPoint;
	private String eCreated;
	
	
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
	public String geteName() {
		return eName;
	}
	public void seteName(String eName) {
		this.eName = eName;
	}
	public String geteSubject() {
		return eSubject;
	}
	public void seteSubject(String eSubject) {
		this.eSubject = eSubject;
	}
	public String geteContent() {
		return eContent;
	}
	public void seteContent(String eContent) {
		this.eContent = eContent;
	}
	public String geteDate() {
		return eDate;
	}
	public void seteDate(String eDate) {
		this.eDate = eDate;
	}
	public String geteFIN() {
		return eFIN;
	}
	public void seteFIN(String eFIN) {
		this.eFIN = eFIN;
	}
	public int getUserPoint() {
		return userPoint;
	}
	public void setUserPoint(int userPoint) {
		this.userPoint = userPoint;
	}
	
	public int geteHitCount() {
		return eHitCount;
	}
	public void seteHitCount(int eHitCount) {
		this.eHitCount = eHitCount;
	}
	
	public String geteCreated() {
		return eCreated;
	}
	public void seteCreated(String eCreated) {
		this.eCreated = eCreated;
	}
	
	
}
