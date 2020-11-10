package com.mypage;

public class TrmyDTO {
	private int classNum;
	private String className;
	
	private long sclassEnabled;
	private long eclassEnabled;
	
	private String userId;
	private String userName;
	private String userTel;
	private String userEmail;
	private long userEnabled;
	private String classStart;
	private String classEnd;
	
	private String stdId;
	private String stdName;
	private String stdEmail;
	
	private long sstdEnabled;
	private long estdEnabled;
	
	
	public int getClassNum() {
		return classNum;
	}
	public void setClassNum(int classNum) {
		this.classNum = classNum;
	}
	public String getClassName() {
		return className;
	}
	public void setClassName(String className) {
		this.className = className;
	}
	
	public String getClassStart() {
		return classStart;
	}
	public void setClassStart(String classStart) {
		this.classStart = classStart;
	}
	public String getClassEnd() {
		return classEnd;
	}
	public void setClassEnd(String classEnd) {
		this.classEnd = classEnd;
	}
	
	public long getSclassEnabled() {
		return sclassEnabled;
	}
	public void setSclassEnabled(long sclassEnabled) {
		this.sclassEnabled = sclassEnabled;
	}
	public long getEclassEnabled() {
		return eclassEnabled;
	}
	public void setEclassEnabled(long eclassEnabled) {
		this.eclassEnabled = eclassEnabled;
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
	public String getStdId() {
		return stdId;
	}
	public void setStdId(String stdId) {
		this.stdId = stdId;
	}
	public String getStdName() {
		return stdName;
	}
	public void setStdName(String stdName) {
		this.stdName = stdName;
	}
	public String getStdEmail() {
		return stdEmail;
	}
	public void setStdEmail(String stdEmail) {
		this.stdEmail = stdEmail;
	}
	
	public long getSstdEnabled() {
		return sstdEnabled;
	}
	public void setSstdEnabled(long sstdEnabled) {
		this.sstdEnabled = sstdEnabled;
	}
	public long getEstdEnabled() {
		return estdEnabled;
	}
	public void setEstdEnabled(long estdEnabled) {
		this.estdEnabled = estdEnabled;
	}
	public String getUserTel() {
		return userTel;
	}
	public void setUserTel(String userTel) {
		this.userTel = userTel;
	}
	public String getUserEmail() {
		return userEmail;
	}
	public void setUserEmail(String userEmail) {
		this.userEmail = userEmail;
	}
	public long getUserEnabled() {
		return userEnabled;
	}
	public void setUserEnabled(long userEnabled) {
		this.userEnabled = userEnabled;
	}
	
	
}
