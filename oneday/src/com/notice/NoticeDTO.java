package com.notice;

public class NoticeDTO {
	private int noNum;
	private String noName;
	private String noSubject;
	private String noContent;
	private String noDate;
	
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
	public String getNoDate() {
		return noDate;
	}
	public void setNoDate(String noDate) {
		this.noDate = noDate;
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
	
}
