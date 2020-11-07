package com.notice;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.SQLException;
import java.util.List;

import com.util.DBConn;

public class NoticeDAOImpl implements NoticeDAO {
	private Connection conn=DBConn.getConnection();
	@Override
	public int insertNotice(NoticeDTO dto) throws SQLException {
		//�ۿø���
		int result=0;
		PreparedStatement pstmt=null;
		String sql;
		
		try {
			sql="INSERT INTO notice(notice, noNum, noName, noSubject, noContent, noCreated, noSFN, noOFN, noHitCount)"
					+ "  VALUES(?, notice_seq.NEXTVAL, ?, ?, ?, SYSDATE, ?, ?, 0)";
			
			pstmt=conn.prepareStatement(sql);
			
			pstmt.setInt(1, dto.getNotice());
			pstmt.setString(2, dto.getNoName());
			pstmt.setString(3, dto.getNoSubject());
			pstmt.setString(4, dto.getNoContent());
			pstmt.setString(5, dto.getNoSaveFileName());
			pstmt.setString(6, dto.getNoOrginalFileName());
			
			result=pstmt.executeUpdate();
			
		} catch (SQLException e) {
			e.printStackTrace();
			throw e;
		} finally {
			if(pstmt!=null) {
				try {
					pstmt.close();
				} catch (Exception e2) {
				}
			}
		}
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
