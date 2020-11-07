package com.review;

public class ReviewDTO {
	private int rvNum;
	private String rvSubject;
	private String rvContent;
	private String rvClassName;
	private String rvScore;
	private int classNum;
	private String userId;
	
	public int getRvNum() {
		return rvNum;
	}
	public void setRvNum(int rvNum) {
		this.rvNum = rvNum;
	}
	public String getRvSubject() {
		return rvSubject;
	}
	public void setRvSubject(String rvSubject) {
		this.rvSubject = rvSubject;
	}
	public String getRvContent() {
		return rvContent;
	}
	public void setRvContent(String rvContent) {
		this.rvContent = rvContent;
	}
	public String getRvClassName() {
		return rvClassName;
	}
	public void setRvClassName(String rvClassName) {
		this.rvClassName = rvClassName;
	}
	public String getRvScore() {
		return rvScore;
	}
	public void setRvScore(String rvScore) {
		this.rvScore = rvScore;
	}
	public int getClassNum() {
		return classNum;
	}
	public void setClassNum(int classNum) {
		this.classNum = classNum;
	}
	public String getUserId() {
		return userId;
	}
	public void setUserId(String userId) {
		this.userId = userId;
	}
}
