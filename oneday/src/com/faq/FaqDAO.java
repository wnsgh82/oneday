package com.faq;

import java.sql.SQLException;
import java.util.List;


public interface FaqDAO {
	public int insertFaq(FaqDTO dto) throws SQLException;
	public int updateFaq(FaqDTO dto) throws SQLException;
	public int deleteFaq(int bNum) throws SQLException;
	
	public int dataCount();
	public int dataCount(String condition, String keyword);
	
	public List<FaqDTO> listFaq(int offset, int rows);
	public List<FaqDTO> listFaq(int offset, int rows, String condition, String keyword);
	
	public FaqDTO readQna(int bNum);
}
