package com.member;

//세션에 정보를 저장할 클래스
public class SessionInfo {
	private String userId;
	private String userName;
	private int userEnabled;
	private String email;
	private String birth;
	private String tel;
	
	public String getEmail() {
		return email;
	}
	public void setEmail(String email) {
		this.email = email;
	}
	public String getBirth() {
		return birth;
	}
	public void setBirth(String birth) {
		this.birth = birth;
	}
	public String getTel() {
		return tel;
	}
	public void setTel(String tel) {
		this.tel = tel;
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
	public int getUserEnabled() {
		return userEnabled;
	}
	public void setUserEnabled(int userEnabled) {
		this.userEnabled = userEnabled;
	}
	
	
	
}
