package com.notice;

import java.sql.SQLException;
import java.util.List;

public interface NoticeDAO {
	public int insertNotice(NoticeDTO dto) throws SQLException;
	public int updateNotice(NoticeDTO dto) throws SQLException;
	
	public int deleteNotice(int num) throws SQLException;
	public int deleteList(int[] nums) throws SQLException;
	
	public int dataCount();
	public int dataCount(String condition, String keyword);
	public int updateHitCount(int num) throws SQLException;

	
	public List<NoticeDTO> listNotice(int offset, int rows);
	public List<NoticeDTO> listNotice(int offset, int rows, String condition, String keyword);
	
	public List<NoticeDTO> listNotice(); //�������ߴ� ������
	
	public NoticeDTO readNotice(int num); //�ش��ȣ ���� �ҷ�����
}
