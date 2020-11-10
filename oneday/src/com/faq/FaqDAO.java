package com.faq;

import java.sql.SQLException;
import java.util.List;


public interface FaqDAO {
	public int insertQna(FaqDTO dto, String mode) throws SQLException;
	public int updateOrderNo(int groupNum, int orderNo) throws SQLException;
	public int updateEnabled(int parent) throws SQLException;

	public int updateQna(FaqDTO dto) throws SQLException;
	public int deleteQna(int bNum) throws SQLException;
	public int updateHitCount(int bNum) throws SQLException;
	
	public int dataCount();
	public int dataCount(String condition, String keyword);
	
	public List<FaqDTO> listQna(int offset, int rows);
	public List<FaqDTO> listQna(int offset, int rows, String condition, String keyword);
	
	public FaqDTO readQna(int bNum);
	public FaqDTO preReadQna(int groupNum, int orderNo, String condition, String keyword);
	public FaqDTO nextReadQna(int groupNum, int orderNo, String condition, String keyword);
}
