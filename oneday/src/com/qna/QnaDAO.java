package com.qna;

import java.sql.SQLException;
import java.util.List;


public interface QnaDAO {
	public int insertQna(QnaDTO dto, String mode) throws SQLException;
	public int updateOrderNo(int groupNum, int orderNo) throws SQLException;
	public int updateQna(QnaDTO dto) throws SQLException;
	public int deleteQna(int bNum) throws SQLException;
	public int updateHitCount(int bNum) throws SQLException;
	
	public int dataCount();
	public int dataCount(String condition, String keyword);
	
	public List<QnaDTO> listQna(int offset, int rows);
	public List<QnaDTO> listQna(int offset, int rows, String condition, String keyword);
	
	public QnaDTO readQna(int bNum);
	public QnaDTO preReadQna(int groupNum, int orderNo, String condition, String keyword);
	public QnaDTO nextReadQna(int groupNum, int orderNo, String condition, String keyword);
}
