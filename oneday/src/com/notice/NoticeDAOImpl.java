package com.notice;

import java.sql.PreparedStatement;
import java.sql.SQLException;
import java.util.List;

public class NoticeDAOImpl implements NoticeDAO {

	@Override
	public int insertNotice(NoticeDTO dto) throws SQLException {
		//�ۿø���
		int result=0;
		PreparedStatement pstmt=null;
		
		String sql;
		
		return result;
	}

	@Override
	public int updateNotice(NoticeDTO dto) throws SQLException {
		//�ۼ���
		
		return 0;
	}

	@Override
	public int deleteNotice(int num) throws SQLException {
		//�ۻ��� (�Խñ۾ȿ���)
		
		return 0;
	}

	@Override
	public int deleteList(int[] nums) throws SQLException {
		//�Խñۻ���(����Ʈ���� üũ�� )
		return 0;
	}

	@Override
	public int dataCount() {
		//�ѵ����Ͱ���
		return 0;
	}

	@Override
	public int dataCount(String condition, String keyword) {
		//�˻��� �����Ͱ���
		return 0;
	}

	@Override
	public List<NoticeDTO> listNotice(int offset, int rows) {
		//�۸���Ʈ
		return null;
	}

	@Override
	public List<NoticeDTO> listNotice(int offset, int rows, String condition, String keyword) {
		//�۸���Ʈ(�˻���)
		
		return null;
	}

	@Override
	public List<NoticeDTO> listNotice() {
		//������ �ö���� ������
		
		return null;
	}

	@Override
	public NoticeDTO readNotice(int num) {
		//��������
		
		return null;
	}

}
