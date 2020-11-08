package com.qna;

public class QnaDTO {
	private int bNum, listNum;
	private String userId, userName;
	private String bSubject;
	private String bContent;
	private int groupNum;
	private int depth;
	private int orderNo;
	private int parent;
	private int bHitCount;
	private String bCreated;
	
	public int getbNum() {
		return bNum;
	}
	public void setbNum(int bNum) {
		this.bNum = bNum;
	}
	public int getListNum() {
		return listNum;
	}
	public void setListNum(int listNum) {
		this.listNum = listNum;
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
	public String getbSubject() {
		return bSubject;
	}
	public void setbSubject(String bSubject) {
		this.bSubject = bSubject;
	}
	public String getbContent() {
		return bContent;
	}
	public void setbContent(String bContent) {
		this.bContent = bContent;
	}
	public int getGroupNum() {
		return groupNum;
	}
	public void setGroupNum(int groupNum) {
		this.groupNum = groupNum;
	}
	public int getDepth() {
		return depth;
	}
	public void setDepth(int depth) {
		this.depth = depth;
	}
	public int getOrderNo() {
		return orderNo;
	}
	public void setOrderNo(int orderNo) {
		this.orderNo = orderNo;
	}
	public int getParent() {
		return parent;
	}
	public void setParent(int parent) {
		this.parent = parent;
	}
	public int getbHitCount() {
		return bHitCount;
	}
	public void setbHitCount(int bHitCount) {
		this.bHitCount = bHitCount;
	}
	public String getbCreated() {
		return bCreated;
	}
	public void setbCreated(String bCreated) {
		this.bCreated = bCreated;
	}
	
}
