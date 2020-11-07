package com.notice;

public class NoticeDTO {
	private int noNum;
	private int listNum;
	private int notice;
	private String noName;
	private String noSubject;
	private String noContent;
	private String noCreated;
	private String noHitCount;
	
	private String noSaveFileName;
	private String noOrginalFileName;
	private long nofileSize;
	
	public int getNoNum() {
		return noNum;
	}
	public void setNoNum(int noNum) {
		this.noNum = noNum;
	}
	public String getNoName() {
		return noName;
	}
	public void setNoName(String noName) {
		this.noName = noName;
	}
	public String getNoSubject() {
		return noSubject;
	}
	public void setNoSubject(String noSubject) {
		this.noSubject = noSubject;
	}
	public String getNoContent() {
		return noContent;
	}
	public void setNoContent(String noContent) {
		this.noContent = noContent;
	}
	public String getNoCreated() {
		return noCreated;
	}
	public void setNoCretaed(String noCreated) {
		this.noCreated = noCreated;
	}
	public String getNoSaveFileName() {
		return noSaveFileName;
	}
	public void setNoSaveFileName(String noSaveFileName) {
		this.noSaveFileName = noSaveFileName;
	}
	public String getNoOrginalFileName() {
		return noOrginalFileName;
	}
	public void setNoOrginalFileName(String noOrginalFileName) {
		this.noOrginalFileName = noOrginalFileName;
	}
	public long getNofileSize() {
		return nofileSize;
	}
	public void setNofileSize(long nofileSize) {
		this.nofileSize = nofileSize;
	}
	public int getNotice() {
		return notice;
	}
	public void setNotice(int notice) {
		this.notice = notice;
	}
	public String getNoHitCount() {
		return noHitCount;
	}
	public void setNoHitCount(String noHitCount) {
		this.noHitCount = noHitCount;
	}
	public int getListNum() {
		return listNum;
	}
	public void setListNum(int listNum) {
		this.listNum = listNum;
	}
	
}
