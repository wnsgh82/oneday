package com.event_backup;

public class EventDTO {
	private int eNum;
	private String userId, userName;
	private String eSubject;
	private String eContent;
	private String eIFN;
	private int eHitCount;
	private int userPoint;
	private String eCreated;
	
	private String eStart, eEnd;
	private long eEnabled;
	
	private int eventApplyEnabled;


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

	public String geteIFN() {
		return eIFN;
	}
	public void seteIFN(String eIFN) {
		this.eIFN = eIFN;
	}
	public int geteHitCount() {
		return eHitCount;
	}
	public void seteHitCount(int eHitCount) {
		this.eHitCount = eHitCount;
	}
	public int getUserPoint() {
		return userPoint;
	}
	public void setUserPoint(int userPoint) {
		this.userPoint = userPoint;
	}
	public String geteCreated() {
		return eCreated;
	}
	public void seteCreated(String eCreated) {
		this.eCreated = eCreated;
	}	
	public String geteStart() {
		return eStart;
	}
	public void seteStart(String eStart) {
		this.eStart = eStart;
	}
	public String geteEnd() {
		return eEnd;
	}
	public void seteEnd(String eEnd) {
		this.eEnd = eEnd;
	}
	public long geteEnabled() {
		return eEnabled;
	}
	public void seteEnabled(long eEnabled) {
		this.eEnabled = eEnabled;
	}
	public int getEventApplyEnabled() {
		return eventApplyEnabled;
	}
	public void setEventApplyEnabled(int eventApplyEnabled) {
		this.eventApplyEnabled = eventApplyEnabled;
	}

	
}
