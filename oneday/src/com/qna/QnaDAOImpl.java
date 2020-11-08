package com.qna;

import java.sql.SQLException;
import java.util.List;

public class QnaDAOImpl implements QnaDAO {

	@Override
	public int insertQna(QnaDTO dto, String mode) throws SQLException {
		
		return 0;
	}

	@Override
	public int updateOrderNo(int groupNum, int orderNo) throws SQLException {
		
		return 0;
	}

	@Override
	public int updateQna(QnaDTO dto) throws SQLException {
		
		return 0;
	}

	@Override
	public int deleteQna(int bNum) throws SQLException {
		
		return 0;
	}

	@Override
	public int updateHitCount(int bNum) throws SQLException {
		
		return 0;
	}

	@Override
	public int dataCount() {
		
		return 0;
	}

	@Override
	public int dataCount(String condition, String keyword) {
		
		return 0;
	}

	@Override
	public List<QnaDTO> listQna(int offset, int rows) {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public List<QnaDTO> listQna(int offset, int rows, String condition, String keyword) {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public QnaDTO readQna(int bNum) {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public QnaDTO preReadQna(int groupNum, int orderNo, String condition, String keyword) {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public QnaDTO nextReadQna(int groupNum, int orderNo, String condition, String keyword) {
		// TODO Auto-generated method stub
		return null;
	}

}
